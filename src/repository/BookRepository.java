package repository;

import model.Book;
import util.ArrayListADT;
import util.FileUtil;
import util.StackADT;

public class BookRepository {
    private static final String BOOKS_FILE = "books.txt";
    private StackADT<Book> bookStack;

    public BookRepository(StackADT<Book> bookStack) {
        this.bookStack = bookStack;
    }

    // Load all books from the file into the stack
    public void loadBooks() {
        ArrayListADT<String> lines = FileUtil.readLines(BOOKS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");
            if (parts.length == 5) {
                Book book = new Book(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])
                );
                bookStack.push(book);
            }
        }
    }

    // Save all books from the stack to the file
    public void saveBooks() {
        ArrayListADT<String> lines = new ArrayListADT<>();
        for (int i = 0; i < bookStack.size(); i++) {
            Book book = bookStack.get(i);
            lines.add(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity() + "," + book.getPrice());
        }
        FileUtil.writeLines(BOOKS_FILE, lines);
    }

    // Add a book to the stack
    public void addBook(Book book) {
        bookStack.push(book);
    }

    // Remove a book from the stack
    public Book removeBook() {
        return bookStack.pop();
    }

    // Get all books from the stack
    public StackADT<Book> getAllBooks() {
        return bookStack;
    }

    // Get the next book ID
    public int getNextBookId() {
        int maxId = 0;
        for (int i = 0; i < bookStack.size(); i++) {
            Book book = bookStack.get(i);
            if (book.getId() > maxId) {
                maxId = book.getId();
            }
        }
        return maxId + 1;
    }

    // Update the quantity of a book
    public void updateBookQuantity(int bookId, int quantity) {
        for (int i = 0; i < bookStack.size(); i++) {
            Book book = bookStack.get(i);
            if (book.getId() == bookId) {
                book.setQuantity(quantity);
                break;
            }
        }
    }
}