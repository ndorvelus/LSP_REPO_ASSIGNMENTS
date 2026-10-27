package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a single book in the Library Lending System.
 * A Book stores its ISBN, title, and author, and maintains its own
 * availability. New books start available. Availability can only be
 * changed through {@link #borrowBook()} and {@link #returnBook()}, which
 * validate the requested state change.
 *
 * @author Nathan Dorvelus
 */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates a new, available book.
     *
     * @param isbn   the book's ISBN
     * @param title  the book's title
     * @param author the book's author
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the book's ISBN.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Reports whether the book is currently available to borrow.
     *
     * @return true if the book is available, false if it is borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Borrows this book, making it unavailable.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        available = false;
    }

    /**
     * Returns this book, making it available again.
     *
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book " + isbn + " is not currently borrowed.");
        }
        available = true;
    }

    /**
     * Returns the book's details in the form
     * {@code B101 Effective Java by Joshua Bloch [AVAILABLE]}, using
     * {@code [BORROWED]} when the book is unavailable.
     *
     * @return a string describing the book and its availability
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}
