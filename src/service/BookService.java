package service;

import model.Book;
import repository.BookRepository;
import util.StackADT;

import java.util.Scanner;

public class BookService {
    private BookRepository bookRepository;

    public BookService(StackADT<Book> bookStack) {
        this.bookRepository = new BookRepository(bookStack);
    }

    public void loadBooks() {
        bookRepository.loadBooks();
    }

    public void saveBooks() {
        bookRepository.saveBooks();
    }

    public void viewBooks() {
        StackADT<Book> books = bookRepository.getAllBooks();
        if (books.size() == 0) {
            System.out.println("No books available.");
            return;
        }
        System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%-10d %-30s %-20s %-10d $%-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
        }
    }

    public void searchBookByAuthor(Scanner scanner) {
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        StackADT<Book> books = bookRepository.getAllBooks();
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getAuthor().equalsIgnoreCase(author)) {
                if (!found) {
                    System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Book ID", "Title", "Author", "Quantity", "Price");
                    System.out.println("---------------------------------------------------------------");
                }
                System.out.printf("%-10d %-30s %-20s %-10d $%-10.2f%n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found by author " + author);
        }
    }

    public Book searchBookById(int id) {
        StackADT<Book> books = bookRepository.getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void editBook(Scanner scanner) {
        System.out.print("Enter book ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = searchBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.print("Enter new title (leave blank to keep current): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            book.setTitle(title);
        }
        System.out.print("Enter new author (leave blank to keep current): ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }
        System.out.print("Enter new quantity (leave blank to keep current): ");
        String quantityStr = scanner.nextLine();
        if (!quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);
            book.setQuantity(quantity);
        }
        System.out.print("Enter new price (leave blank to keep current): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            double price = Double.parseDouble(priceStr);
            book.setPrice(price);
        }
        System.out.println("Book updated successfully.");
    }

    public void addBook(Scanner scanner) {
        int id = bookRepository.getNextBookId();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Book book = new Book(id, title, author, quantity, price);
        bookRepository.addBook(book);
        System.out.println("Book added successfully.");
    }

    public void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = searchBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        bookRepository.removeBook();
        System.out.println("Book deleted successfully.");
    }
}