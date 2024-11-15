package service;

import model.Book;
import model.Customer;
import model.Order;
import repository.BookRepository;
import repository.OrderRepository;

import java.util.List;
import java.util.Scanner;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private BookRepository bookRepository = new BookRepository();
    private SortingService sortingService = new SortingService();
    private SearchingService searchingService = new SearchingService();

    // Load orders to set the correct order ID counter
    public void loadOrders() {
        orderRepository.getAllOrders();
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
            Book book = searchBookById(bookId);
            if (book != null) {
                System.out.println("Book found: " + book);
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity <= book.getQuantity()) {
                    books[i] = new Book(book.getId(), book.getTitle(), book.getAuthor(), quantity, book.getPrice());
                    totalPrice += book.getPrice() * quantity;
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
            List<Order> orders = orderRepository.getAllOrders();
            orders.add(order);
            orderRepository.saveOrders(orders);
            System.out.println("Order placed successfully.");
            displayOrder(order);
        } else {
            System.out.println("Order cancelled.");
        }
    }

    // Search for a book by its ID
    private Book searchBookById(int id) {
        List<Book> books = bookRepository.getAllBooks();
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Process the next order in the queue
    public void processOrder() {
        List<Order> orders = orderRepository.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }
        Order order = orders.remove(0);
        orderRepository.saveOrders(orders);
        System.out.println("Processing order: " + order);
    }

    // View all orders in a formatted table
    public void viewOrders() {
        List<Order> orders = orderRepository.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders to view.");
            return;
        }
        System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-10s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        for (Order order : orders) {
            displayOrder(order);
        }
    }

    // View orders for a specific customer
    public void viewMyOrders(Scanner scanner, String customerName) {
        List<Order> orders = orderRepository.getAllOrders();
        boolean found = false;
        for (Order order : orders) {
            if (order.getCustomer().getName().equalsIgnoreCase(customerName)) {
                if (!found) {
                    System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price");
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
                }
                displayOrder(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No orders found for " + customerName);
        }
    }

    // Search for an order by its ID
    public void searchOrder(Scanner scanner) {
        System.out.print("Enter order ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Order> orders = orderRepository.getAllOrders();
        Order[] ordersArray = orders.toArray(new Order[0]);
        sortingService.quickSort(ordersArray, 0, ordersArray.length - 1);
        int index = searchingService.binarySearch(ordersArray, id);
        if (index != -1) {
            Order order = ordersArray[index];
            System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15s%n", "Order ID", "Customer", "Shipping Address", "Books", "Quantity", "Price", "Total Price");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            displayOrder(order);
        } else {
            System.out.println("Order not found.");
        }
    }

    // Display an order in a formatted table
    private void displayOrder(Order order) {
        System.out.printf("%-10s %-20s %-30s", order.getId(), order.getCustomer().getName(), order.getCustomer().getAddress());
        Book[] books = order.getBooks();
        double totalPrice = 0;
        if (books.length > 0) {
            System.out.printf(" %-30s %-10d %-10.2f %-10.2f%n", books[0].getTitle(), books[0].getQuantity(), books[0].getPrice(), books[0].getPrice() * books[0].getQuantity());
            totalPrice += books[0].getPrice() * books[0].getQuantity();
            for (int i = 1; i < books.length; i++) {
                System.out.printf("%-10s %-20s %-30s %-30s %-10d %-10.2f %-10.2f%n", "", "", "", books[i].getTitle(), books[i].getQuantity(), books[i].getPrice(), books[i].getPrice() * books[i].getQuantity());
                totalPrice += books[i].getPrice() * books[i].getQuantity();
            }
        }
        System.out.printf("%-10s %-20s %-30s %-30s %-10s %-10s %-15.2f%n", "", "", "", "", "", "Total:", totalPrice);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    // Display order details for confirmation
    private void displayOrderDetails(Customer customer, Book[] books, double totalPrice) {
        System.out.printf("%-20s: %s%n", "Customer", customer.getName());
        System.out.printf("%-20s: %s%n", "Shipping Address", customer.getAddress());
        System.out.printf("%-10s %-30s %-20s %-10s %-10s %-10s%n", "ID", "Title", "Author", "Quantity", "Price", "Total Price");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("%-10d %-30s %-20s %-10d %-10.2f %-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice(), book.getPrice() * book.getQuantity());
        }
        System.out.printf("Total Price: $%.2f%n", totalPrice);
    }
}