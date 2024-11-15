package repository;

import model.Book;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final String BOOKS_FILE = "books.txt";

    // Get all books from the file
    public List<Book> getAllBooks() {
        List<String> lines = FileUtil.readLines(BOOKS_FILE);
        List<Book> books = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                Book book = new Book(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])
                );
                books.add(book);
            }
        }
        return books;
    }

    // Save all books to the file
    public void saveBooks(List<Book> books) {
        List<String> lines = new ArrayList<>();
        for (Book book : books) {
            lines.add(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity() + "," + book.getPrice());
        }
        FileUtil.writeLines(BOOKS_FILE, lines);
    }

    // Get the next book ID
    public int getNextBookId() {
        List<Book> books = getAllBooks();
        int maxId = 0;
        for (Book book : books) {
            if (book.getId() > maxId) {
                maxId = book.getId();
            }
        }
        return maxId + 1;
    }
}