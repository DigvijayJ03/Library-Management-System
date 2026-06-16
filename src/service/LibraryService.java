package src.service;

import src.exception.BookNotFoundException;
import src.exception.BookUnavailableException;
import src.exception.UserNotFoundException;
import src.model.Book;
import src.model.User;

public interface LibraryService {
    void addBook(Book book);

    void displayBooks();

    Book searchBook(int id);

    Book searchBookByTitle(String title);

    void deleteBook(int id);

    void issueBook(int bookId, int userId)
            throws BookNotFoundException,
            UserNotFoundException,
            BookUnavailableException;

    void returnBook(int transactionId, int bookId);

    void addUser(User user);

    void viewUsers();

    void showTransactions();

    void showLibraryStatistics();

    void sortBooksByTitle();

    void sortBooksByQuantity();

    void showDashboard();

    void showTransactionHistory();

    void showFine(int transactionId);

    void showOverdueBooks();

    void searchBooksByAuthor(String author);

    void showAvailableBooks();

    void showOutOfStockBooks();

    void sortBooksByTitleSQL();

    void sortBooksByQuantitySQL();

}
