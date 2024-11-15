package controller;

import model.Book;
import service.BookService;

import java.util.Scanner;

public class BookController {
    private BookService bookService = new BookService();

    // View all books
    public void viewBooks() {
        bookService.viewBooks();
    }

    // Search for a book by its ID
    public void searchBook(Scanner scanner) {
        System.out.print("Enter book ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.searchBookById(id);
        if (book != null) {
            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
            System.out.println("--------------------------------------------------------------------------------");
            displayBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    // Display a book in a formatted table
    private void displayBook(Book book) {
        System.out.printf("%-10d %-30s %-20s %-10d %-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
    }

    // Edit a book
    public void editBook(Scanner scanner) {
        bookService.editBook(scanner);
    }

    // Add a new book
    public void addBook(Scanner scanner) {
        bookService.addBook(scanner);
    }

    // Delete a book
    public void deleteBook(Scanner scanner) {
        bookService.deleteBook(scanner);
    }
}