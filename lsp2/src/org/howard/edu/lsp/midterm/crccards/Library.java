package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a collection of {@link Book} objects and coordinates borrowing
 * and returning them. Books are stored in a {@link HashMap} keyed by ISBN,
 * which gives constant-time lookup and makes duplicate ISBNs easy to detect.
 * The Library enforces a limit of three books borrowed at one time and
 * delegates each book's state change to the Book itself.
 *
 * @author Nathan Dorvelus
 */
public class Library {

    /** Maximum number of books that may be borrowed from the Library at once. */
    private static final int MAX_BORROWED = 3;

    private final Map<String, Book> books = new HashMap<>();
    private int borrowedCount = 0;

    /**
     * Creates an empty Library.
     */
    public Library() {
    }

    /**
     * Adds a book to the Library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if the book is null or a book with the
     *                                  same ISBN is already in the Library
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds the book with the given ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book, or null if no book has that ISBN
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is already borrowed or the
     *                                  three-book borrowing limit has been reached
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        if (borrowedCount >= MAX_BORROWED) {
            throw new IllegalStateException("Borrowing limit of " + MAX_BORROWED + " books reached.");
        }
        book.borrowBook();
        borrowedCount++;
    }

    /**
     * Returns the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        Book book = requireBook(isbn);
        book.returnBook();
        borrowedCount--;
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a new list of available books; empty if there are none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books whose author exactly matches the given name
     * (case-sensitive), including borrowed books.
     *
     * @param author the author name to match
     * @return a new list of matching books; empty if there are none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical order,
     * regardless of availability. The Library itself is not changed.
     *
     * @return a new sorted list of all books; empty if the Library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort((a, b) -> a.getIsbn().compareTo(b.getIsbn()));
        return result;
    }

    /**
     * Returns the number of books currently borrowed from the Library.
     *
     * @return the borrowed book count
     */
    public int getBorrowedBookCount() {
        return borrowedCount;
    }

    /**
     * Looks up a book by ISBN, failing if it is not in the Library.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book
     * @throws IllegalArgumentException if no book has that ISBN
     */
    private Book requireBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN " + isbn + " in the library.");
        }
        return book;
    }
}
