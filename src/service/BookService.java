package service;

import model.Book;
import repository.BookRepository;

import java.util.List;
import java.util.Scanner;

public class BookService {
    private BookRepository bookRepository = new BookRepository();

    // View all books in a formatted table
    public void viewBooks() {
        List<Book> books = bookRepository.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books to view.");
            return;
        }
        System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
        System.out.println("--------------------------------------------------------------------------------");
        for (Book book : books) {
            displayBook(book);
        }
    }

    // Search for a book by its ID
    public void searchBook(Scanner scanner) {
        System.out.print("Enter book ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = searchBookById(id);
        if (book != null) {
            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
            System.out.println("--------------------------------------------------------------------------------");
            displayBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    // Search for a book by its ID
    public Book searchBookById(int id) {
        List<Book> books = bookRepository.getAllBooks();
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Display a book in a formatted table
    private void displayBook(Book book) {
        System.out.printf("%-10d %-30s %-20s %-10d %-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
    }

    // Edit a book
    public void editBook(Scanner scanner) {
        System.out.print("Enter book ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = searchBookById(id);
        if (book != null) {
            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
            System.out.println("--------------------------------------------------------------------------------");
            displayBook(book);
            System.out.print("Do you want to edit this book? (yes/no): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                System.out.println("Which part do you want to edit?");
                System.out.printf("%-5s %-20s%n", "1.", "Title");
                System.out.printf("%-5s %-20s%n", "2.", "Author");
                System.out.printf("%-5s %-20s%n", "3.", "Quantity");
                System.out.printf("%-5s %-20s%n", "4.", "Price");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter new title: ");
                        String title = scanner.nextLine();
                        book.setTitle(title);
                        break;
                    case 2:
                        System.out.print("Enter new author: ");
                        String author = scanner.nextLine();
                        book.setAuthor(author);
                        break;
                    case 3:
                        System.out.print("Enter new quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        book.setQuantity(quantity);
                        break;
                    case 4:
                        System.out.print("Enter new price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        book.setPrice(price);
                        break;
                    default:
                        System.out.println("Invalid choice. Edit cancelled.");
                        return;
                }
                System.out.println("Updated book information:");
                displayBook(book);
                System.out.print("Do you want to save these changes? (yes/no): ");
                String saveConfirm = scanner.nextLine();
                if (saveConfirm.equalsIgnoreCase("yes") || saveConfirm.equalsIgnoreCase("y")) {
                    List<Book> books = bookRepository.getAllBooks();
                    for (int i = 0; i < books.size(); i++) {
                        if (books.get(i).getId() == book.getId()) {
                            books.set(i, book);
                            break;
                        }
                    }
                    bookRepository.saveBooks(books);
                    System.out.println("Book updated successfully.");
                } else {
                    System.out.println("Changes discarded.");
                }
            } else {
                System.out.println("Edit cancelled.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    // Add a new book
    public void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Book book = new Book(bookRepository.getNextBookId(), title, author, quantity, price);
        List<Book> books = bookRepository.getAllBooks();
        books.add(book);
        bookRepository.saveBooks(books);
        System.out.println("Book added successfully.");
    }

    // Delete a book
    public void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Book> books = bookRepository.getAllBooks();
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getId() == id) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            bookRepository.saveBooks(books);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
}