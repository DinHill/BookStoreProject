package service;

import util.QueueADT;
import util.StackADT;
import util.ArrayListADT;
import model.Book;
import model.Customer;
import model.Order;
import repository.OrderRepository;
import repository.BookRepository;

import java.util.Scanner;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private BookService bookService;
    private BookRepository bookRepository;

    public OrderService(StackADT<Book> bookStack) {
        this.bookService = new BookService(bookStack);
        this.bookRepository = new BookRepository(bookStack);
    }

    // Load orders to set the correct order ID counter
    public void loadOrders() {
        orderRepository.loadOrders();
    }

    // Save orders to the file
    public void saveOrders() {
        orderRepository.saveOrders();
    }

    // Place a new order
    public void placeOrder(Scanner scanner, String customerName) {
        System.out.print("Enter shipping address: ");
        String address = scanner.nextLine();
        Customer customer = new Customer(customerName, address);
        System.out.print("Enter number of books: ");
        int numBooks = scanner.nextInt();
        scanner.nextLine();
        Book[] books = new Book[numBooks];
        double totalPrice = 0;
        for (int i = 0; i < numBooks; i++) {
            System.out.print("Enter book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            Book book = bookService.searchBookById(bookId);
            if (book != null) {
                System.out.println("Book found: " + book);
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity <= book.getQuantity()) {
                    books[i] = new Book(book.getId(), book.getTitle(), book.getAuthor(), quantity, book.getPrice());
                    totalPrice += book.getPrice() * quantity;
                    bookRepository.updateBookQuantity(book.getId(), book.getQuantity() - quantity);
                    System.out.println("Book added to order.");
                } else {
                    System.out.println("Insufficient quantity available.");
                    i--; // Retry this book
                }
            } else {
                System.out.println("Book not found.");
                i--; // Retry this book
            }
        }
        // Print order details for confirmation
        System.out.println("Order details:");
        displayOrderDetails(customer, books, totalPrice);
        System.out.println("Confirm order? (yes/no)");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            Order order = new Order(customer, books);
            orderRepository.addOrder(order);
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("Order cancelled.");
        }
    }

    // View orders for a specific customer
    public void viewMyOrders(Scanner scanner, String customerName) {
        ArrayListADT<Order> orders = convertQueueToList(orderRepository.getAllOrders());
        boolean found = false;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getCustomer().getName().equalsIgnoreCase(customerName)) {
                if (!found) {
                    System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15s %-10s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price", "Status");
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                displayOrder(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No orders found for " + customerName);
        }
    }

    // Process the next pending order in the queue
    public void processOrder() {
        Order order = orderRepository.processOrder();
        if (order != null) {
            System.out.println("Processing order: " + order);
            System.out.println("Order processed successfully.");
        } else {
            System.out.println("No pending orders to process.");
        }
    }

    // View all orders
    public void viewOrders() {
        ArrayListADT<Order> orders = convertQueueToList(orderRepository.getAllOrders());
        if (orders.size() == 0) {
            System.out.println("No orders to view.");
            return;
        }
        System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15s %-10s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < orders.size(); i++) {
            displayOrder(orders.get(i));
        }
    }

    // Search for an order by its ID
    public void searchOrder(Scanner scanner) {
        System.out.print("Enter order ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        ArrayListADT<Order> orders = convertQueueToList(orderRepository.getAllOrders());
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getId() == id) {
                System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15s %-10s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price", "Status");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                displayOrder(order);
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private void displayOrderDetails(Customer customer, Book[] books, double totalPrice) {
        System.out.println("Customer: " + customer.getName() + ", Address: " + customer.getAddress());
        System.out.println("Books:");
        for (Book book : books) {
            System.out.printf("%-10d %-30s %-20s %-10d $%-10.2f $%-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice(), book.getPrice() * book.getQuantity());
        }
        System.out.println("Total Price: $" + totalPrice);
    }

    private void displayOrder(Order order) {
        for (Book book : order.getBooks()) {
            System.out.printf("%-10d %-20s %-30s %-30s %-10d $%-10.2f $%-10.2f %-10s%n", order.getId(), order.getCustomer().getName(), order.getCustomer().getAddress(), book.getTitle(), book.getQuantity(), book.getPrice(), book.getPrice() * book.getQuantity(), order.getStatus());
        }
    }

    private ArrayListADT<Order> convertQueueToList(QueueADT<Order> queue) {
        ArrayListADT<Order> list = new ArrayListADT<>();
        for (int i = 0; i < queue.size(); i++) {
            list.add(queue.get(i));
        }
        return list;
    }
}