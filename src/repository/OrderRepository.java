package repository;

import model.Book;
import model.Customer;
import model.Order;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final String ORDERS_FILE = "orders.txt";

    // Get all orders from the file
    public List<Order> getAllOrders() {
        List<String> lines = FileUtil.readLines(ORDERS_FILE);
        List<Order> orders = new ArrayList<>();
        int maxId = 0;
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length == 3) {
                String[] customerParts = parts[0].split(",");
                Customer customer = new Customer(customerParts[0], customerParts[1]);
                String[] bookParts = parts[1].split(",");
                List<Book> books = new ArrayList<>();
                for (String bookPart : bookParts) {
                    String[] bookDetails = bookPart.split(":");
                    books.add(new Book(Integer.parseInt(bookDetails[0]), bookDetails[1], bookDetails[2], Integer.parseInt(bookDetails[3]), Double.parseDouble(bookDetails[4])));
                }
                Order order = new Order(customer, books.toArray(new Book[0]));
                order.setId(Integer.parseInt(parts[2]));
                orders.add(order);
                if (order.getId() > maxId) {
                    maxId = order.getId();
                }
            }
        }
        Order.setIdCounter(maxId);
        return orders;
    }

    // Save all orders to the file
    public void saveOrders(List<Order> orders) {
        List<String> lines = new ArrayList<>();
        for (Order order : orders) {
            StringBuilder line = new StringBuilder();
            line.append(order.getCustomer().getName()).append(",").append(order.getCustomer().getAddress()).append(";");
            for (Book book : order.getBooks()) {
                line.append(book.getId()).append(":").append(book.getTitle()).append(":").append(book.getAuthor()).append(":").append(book.getQuantity()).append(":").append(book.getPrice()).append(",");
            }
            line.deleteCharAt(line.length() - 1); // Remove last comma
            line.append(";").append(order.getId());
            lines.add(line.toString());
        }
        FileUtil.writeLines(ORDERS_FILE, lines);
    }
}