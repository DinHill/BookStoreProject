import controller.BookController;
import controller.OrderController;
import util.NavigationStack;
import util.StackADT;
import model.Book;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        initializeFileStorage();
        Scanner scanner = new Scanner(System.in);
        StackADT<Book> bookStack = new StackADT<>();
        OrderController orderController = new OrderController(bookStack);
        BookController bookController = new BookController(bookStack);
        NavigationStack navigationStack = new NavigationStack();

        // Load existing orders and books
        orderController.loadOrders();
        bookController.loadBooks();
        navigationStack.pushPage("Home");

        while (true) {
            String currentPage = navigationStack.peekPage();
            System.out.println("\nCurrent Page: " + currentPage);
            if ("Home".equals(currentPage)) {
                showHomePage(scanner, navigationStack, bookController, orderController);
            } else if ("Customer".equals(currentPage)) {
                customerFlow(scanner, orderController, bookController, navigationStack);
            } else if ("Admin".equals(currentPage)) {
                adminFlow(scanner, orderController, bookController, navigationStack);
            }
        }
    }

    // Initialize file storage for books and orders
    private static void initializeFileStorage() {
        try {
            new File("books.txt").createNewFile();
            new File("orders.txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show home page options
    private static void showHomePage(Scanner scanner, NavigationStack navigationStack, BookController bookController, OrderController orderController) {
        System.out.println("\nWelcome to the Book Store");
        System.out.printf("%-5s %-20s%n", "1.", "Customer");
        System.out.printf("%-5s %-20s%n", "2.", "Admin");
        System.out.printf("%-5s %-20s%n", "3.", "Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                navigationStack.pushPage("Customer");
                break;
            case 2:
                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();
                if ("admin123".equals(password)) {
                    navigationStack.pushPage("Admin");
                } else {
                    System.out.println("Incorrect password. Access denied.");
                }
                break;
            case 3:
                saveDataAndExit(bookController, orderController);
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Handle customer flow
    private static void customerFlow(Scanner scanner, OrderController orderController, BookController bookController, NavigationStack navigationStack) {
        while (true) {
            System.out.println("\nCustomer Menu");
            System.out.printf("%-5s %-20s%n", "1.", "Place Order");
            System.out.printf("%-5s %-20s%n", "2.", "View Books");
            System.out.printf("%-5s %-20s%n", "3.", "View My Orders");
            System.out.printf("%-5s %-20s%n", "4.", "Search for Book by Author");
            System.out.printf("%-5s %-20s%n", "5.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    orderController.placeOrder(scanner, customerName);
                    break;
                case 2:
                    bookController.viewBooks();
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String customerNameForOrders = scanner.nextLine();
                    orderController.viewMyOrders(scanner, customerNameForOrders);
                    break;
                case 4:
                    bookController.searchBookByAuthor(scanner);
                    break;
                case 5:
                    navigationStack.popPage();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle admin flow
    private static void adminFlow(Scanner scanner, OrderController orderController, BookController bookController, NavigationStack navigationStack) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.printf("%-5s %-20s%n", "1.", "Manage Books");
            System.out.printf("%-5s %-20s%n", "2.", "Manage Orders");
            System.out.printf("%-5s %-20s%n", "3.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    editBookFlow(scanner, bookController);
                    break;
                case 2:
                    manageOrdersFlow(scanner, orderController);
                    break;
                case 3:
                    navigationStack.popPage();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle order management flow for admin
    private static void manageOrdersFlow(Scanner scanner, OrderController orderController) {
        while (true) {
            System.out.println("\nManage Orders");
            System.out.printf("%-5s %-20s%n", "1.", "View Orders");
            System.out.printf("%-5s %-20s%n", "2.", "Process Order");
            System.out.printf("%-5s %-20s%n", "3.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    orderController.viewOrders();
                    break;
                case 2:
                    orderController.processOrder();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle book editing flow for admin
    private static void editBookFlow(Scanner scanner, BookController bookController) {
        while (true) {
            System.out.println("\nManage Books");
            System.out.printf("%-5s %-20s%n", "1.", "View Books");
            System.out.printf("%-5s %-20s%n", "2.", "Edit Book");
            System.out.printf("%-5s %-20s%n", "3.", "Add Book");
            System.out.printf("%-5s %-20s%n", "4.", "Delete Book");
            System.out.printf("%-5s %-20s%n", "5.", "Search for Book by Author");
            System.out.printf("%-5s %-20s%n", "6.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    bookController.viewBooks();
                    break;
                case 2:
                    bookController.editBook(scanner);
                    break;
                case 3:
                    bookController.addBook(scanner);
                    break;
                case 4:
                    bookController.deleteBook(scanner);
                    break;
                case 5:
                    bookController.searchBookByAuthor(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Save data and exit the program
    private static void saveDataAndExit(BookController bookController, OrderController orderController) {
        orderController.saveOrders();
        bookController.saveBooks();
        System.out.println("Data saved successfully. Exiting program.");
    }
}