package controller;

import model.Book;
import service.BookService;
import util.StackADT;

import java.util.Scanner;

public class BookController {
    private BookService bookService;

    public BookController(StackADT<Book> bookStack) {
        this.bookService = new BookService(bookStack);
    }

    public void loadBooks() {
        bookService.loadBooks();
    }

    public void saveBooks() {
        bookService.saveBooks();
    }

    public void viewBooks() {
        bookService.viewBooks();
    }

    public void searchBookByAuthor(Scanner scanner) {
        bookService.searchBookByAuthor(scanner);
    }

    public void editBook(Scanner scanner) {
        bookService.editBook(scanner);
    }

    public void addBook(Scanner scanner) {
        bookService.addBook(scanner);
    }

    public void deleteBook(Scanner scanner) {
        bookService.deleteBook(scanner);
    }
}