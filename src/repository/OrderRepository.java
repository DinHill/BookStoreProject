package repository;

import model.Book;
import model.Customer;
import model.Order;
import util.ArrayListADT;
import util.FileUtil;
import util.QueueADT;

public class OrderRepository {
    private static final String ORDERS_FILE = "orders.txt";
    private QueueADT<Order> orderQueue = new QueueADT<>();

    // Load all orders from the file into the queue
    public void loadOrders() {
        ArrayListADT<String> lines = FileUtil.readLines(ORDERS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(";");
            if (parts.length == 4) { // Update to handle status
                String[] customerParts = parts[0].split(",");
                Customer customer = new Customer(customerParts[0], customerParts[1]);
                String[] bookParts = parts[1].split(",");
                Book[] books = new Book[bookParts.length / 5];
                for (int j = 0; j < bookParts.length; j += 5) {
                    books[j / 5] = new Book(
                            Integer.parseInt(bookParts[j]),
                            bookParts[j + 1],
                            bookParts[j + 2],
                            Integer.parseInt(bookParts[j + 3]),
                            Double.parseDouble(bookParts[j + 4])
                    );
                }
                Order order = new Order(customer, books);
                order.setId(Integer.parseInt(parts[2]));
                order.setStatus(parts[3]); // Set status
                orderQueue.enqueue(order);
            }
        }
    }

    // Save all orders from the queue to the file
    public void saveOrders() {
        ArrayListADT<String> lines = new ArrayListADT<>();
        for (int i = 0; i < orderQueue.size(); i++) {
            Order order = orderQueue.get(i);
            StringBuilder bookString = new StringBuilder();
            for (Book book : order.getBooks()) {
                bookString.append(book.getId()).append(",")
                        .append(book.getTitle()).append(",")
                        .append(book.getAuthor()).append(",")
                        .append(book.getQuantity()).append(",")
                        .append(book.getPrice()).append(",");
            }
            lines.add(order.getCustomer().getName() + "," + order.getCustomer().getAddress() + ";" + bookString + ";" + order.getId() + ";" + order.getStatus());
        }
        FileUtil.writeLines(ORDERS_FILE, lines);
    }

    // Add an order to the queue
    public void addOrder(Order order) {
        orderQueue.enqueue(order);
    }

    // Process the next pending order in the queue
    public Order processOrder() {
        for (int i = 0; i < orderQueue.size(); i++) {
            Order order = orderQueue.get(i);
            if ("Pending".equals(order.getStatus())) {
                order.setStatus("Processing");
                return order;
            }
        }
        return null;
    }

    // Get all orders from the queue
    public QueueADT<Order> getAllOrders() {
        return orderQueue;
    }
}