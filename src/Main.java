import controller.OrderController;
import controller.BookController;
import util.NavigationStack;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        initializeFileStorage();
        Scanner scanner = new Scanner(System.in);
        OrderController orderController = new OrderController();
        BookController bookController = new BookController();
        NavigationStack navigationStack = new NavigationStack();

        // Load existing orders to set the correct order ID counter
        orderController.loadOrders();

        navigationStack.pushPage("Home");

        while (true) {
            String currentPage = navigationStack.peekPage();
            System.out.println("\nCurrent Page: " + currentPage);
            if ("Home".equals(currentPage)) {
                showHomePage(scanner, navigationStack);
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

    // Show home page
    private static void showHomePage(Scanner scanner, NavigationStack navigationStack) {
        System.out.println("\nWelcome to 1649A Book Store");
        System.out.println("Are you a Customer or Admin?");
        System.out.printf("%-5s %-10s%n", "1.", "Customer");
        System.out.printf("%-5s %-10s%n", "2.", "Admin");
        System.out.printf("%-5s %-10s%n", "3.", "Exit");
        System.out.print("Choose an option: ");
        int userType = scanner.nextInt();
        scanner.nextLine();
        switch (userType) {
            case 1:
                navigationStack.pushPage("Customer");
                break;
            case 2:
                navigationStack.pushPage("Admin");
                break;
            case 3:
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Handle customer flow
    private static void customerFlow(Scanner scanner, OrderController orderController, BookController bookController, NavigationStack navigationStack) {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        while (true) {
            System.out.println("\nCustomer Menu");
            System.out.printf("%-5s %-20s%n", "1.", "View Books");
            System.out.printf("%-5s %-20s%n", "2.", "Place Order");
            System.out.printf("%-5s %-20s%n", "3.", "View My Orders");
            System.out.printf("%-5s %-20s%n", "4.", "Search for Book");
            System.out.printf("%-5s %-20s%n", "5.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    bookController.viewBooks();
                    break;
                case 2:
                    orderController.placeOrder(scanner, customerName);
                    break;
                case 3:
                    orderController.viewMyOrders(scanner, customerName);
                    break;
                case 4:
                    bookController.searchBook(scanner);
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
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        if (!"admin123".equals(password)) {
            System.out.println("Incorrect password.");
            navigationStack.popPage();
            return;
        }
        while (true) {
            System.out.println("\nAdmin Panel");
            System.out.printf("%-5s %-20s%n", "1.", "View Books");
            System.out.printf("%-5s %-20s%n", "2.", "Manage Books");
            System.out.printf("%-5s %-20s%n", "3.", "View Orders");
            System.out.printf("%-5s %-20s%n", "4.", "Search Order");
            System.out.printf("%-5s %-20s%n", "5.", "Process Order");
            System.out.printf("%-5s %-20s%n", "6.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    bookController.viewBooks();
                    break;
                case 2:
                    editBookFlow(scanner, bookController);
                    break;
                case 3:
                    orderController.viewOrders();
                    break;
                case 4:
                    orderController.searchOrder(scanner);
                    break;
                case 5:
                    orderController.processOrder();
                    break;
                case 6:
                    navigationStack.popPage();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle book editing flow for admin
    // Handle book editing flow for admin
    private static void editBookFlow(Scanner scanner, BookController bookController) {
        while (true) {
            System.out.println("\nManage Books");
            System.out.printf("%-5s %-20s%n", "1.", "Edit Book");
            System.out.printf("%-5s %-20s%n", "2.", "Add Book");
            System.out.printf("%-5s %-20s%n", "3.", "Delete Book");
            System.out.printf("%-5s %-20s%n", "4.", "Search for Book");
            System.out.printf("%-5s %-20s%n", "5.", "Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    bookController.editBook(scanner);
                    break;
                case 2:
                    bookController.addBook(scanner);
                    break;
                case 3:
                    bookController.deleteBook(scanner);
                    break;
                case 4:
                    bookController.searchBook(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}