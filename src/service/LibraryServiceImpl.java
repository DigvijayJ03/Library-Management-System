package src.service;


import src.exception.BookNotFoundException;
import src.exception.BookUnavailableException;
import src.exception.UserNotFoundException;
import src.model.Book;
import src.model.Transaction;
import src.model.User;
import src.repository.BookRepository;
import src.repository.TransactionRepository;
import src.repository.UserRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.ArrayList;

public class LibraryServiceImpl implements LibraryService {

    public LibraryServiceImpl() {
    }

    private BookRepository bookRepository =
            new BookRepository();

    private UserRepository userRepository =
            new UserRepository();

    private TransactionRepository transactionRepository =
            new TransactionRepository();


    //Add users
    @Override
    public void addUser(User user) {

        if(userRepository.findUserById(user.getUserId()) != null) {

            System.out.println("User ID already exists.");
            return;
        }

        userRepository.addUser(user);

        System.out.println("User Added Successfully.");
    }

    //Search User
    public User searchUser(int userId){

        return userRepository.findUserById(userId);
    }

    //View Users
    @Override
    public void viewUsers() {

        userRepository.getAllUsers()
                .forEach(System.out::println);
    }

    // Add Book
    @Override
    public void addBook(Book book) {

        if(bookRepository.findBookById(book.getId()) != null) {

            System.out.println("Book ID already exists.");
            return;
        }

        bookRepository.addBook(book);

        System.out.println("Book Added Successfully.");
    }

    // Display Books
    @Override
    public void displayBooks() {

        bookRepository.getAllBooks()
                .forEach(System.out::println);
    }

    // Search Book

    public Book searchBook(int id) {

        return bookRepository.findBookById(id);
    }

    // Delete Book
    @Override
    public void deleteBook(int id) {

        if(bookRepository.findBookById(id) == null) {

            System.out.println("Book Not Found.");
            return;
        }

        bookRepository.deleteBook(id);

        System.out.println("Book Deleted Successfully.");
    }

    // Issue Book
    public void issueBook(int bookId, int userId)
            throws BookNotFoundException,
            UserNotFoundException,
            BookUnavailableException {

        Book book =
                bookRepository.findBookById(bookId);

        if(book == null)
            throw new BookNotFoundException(
                    "Book Not Found"
            );

        User user =
                userRepository.findUserById(userId);

        if(user == null)
            throw new UserNotFoundException(
                    "User Not Found"
            );

        if(book.getQuantity() <= 0)
            throw new BookUnavailableException(
                    "Book Out Of Stock"
            );

        bookRepository.updateQuantity(
                bookId,
                book.getQuantity() - 1
        );

        transactionRepository.addTransaction(
                bookId,
                userId,
                java.time.LocalDate.now()
        );

        System.out.println(
                "Book Issued Successfully"
        );
    }

    @Override
    public void showTransactions() {

        ArrayList<Transaction> transactions =
                transactionRepository
                        .getAllTransactions();

        if(transactions.isEmpty()) {

            System.out.println(
                    "No Transactions Found"
            );

            return;
        }

        transactions.forEach(
                System.out::println
        );
    }

    // Return Book
    public void returnBook(
            int transactionId,
            int bookId) {

        Book book =
                bookRepository.findBookById(bookId);

        if(book == null) {

            System.out.println(
                    "Book Not Found"
            );

            return;
        }

        bookRepository.updateQuantity(
                bookId,
                book.getQuantity() + 1
        );

        transactionRepository
                .returnBook(transactionId);

        System.out.println(
                "Book Returned Successfully"
        );
    }







    @Override
    public void showLibraryStatistics() {

        System.out.println("Total Books : "
                + bookRepository.getAllBooks().size());

        System.out.println("Total Users : "
                + userRepository.getAllUsers().size());

        System.out.println("Total Transactions : "
                + transactionRepository.getAllTransactions().size());
    }

    public Book searchBookByTitle(String title){

        return bookRepository.findBookByTitle(title);
    }

    public void sortBooksByTitle() {

        ArrayList<Book> books =
                bookRepository.getAllBooks();

        books.sort(
                Comparator.comparing(Book::getTitle)
        );

        books.forEach(System.out::println);
    }

    public void sortBooksByQuantity() {

        ArrayList<Book> books =
                bookRepository.getAllBooks();

        books.sort(
                Comparator.comparingInt(
                        Book::getQuantity
                )
        );

        books.forEach(System.out::println);
    }

    public void advancedStatistics() {

        long availableBooks =
                bookRepository
                        .getAllBooks()
                        .stream()
                        .filter(book ->
                                book.getQuantity() > 0)
                        .count();

        System.out.println(
                "Available Books : "
                        + availableBooks
        );
    }

    public void mostStockedBook() {

        Book book =
                bookRepository
                        .getAllBooks()
                        .stream()
                        .max(
                                Comparator.comparingInt(
                                        Book::getQuantity
                                )
                        )
                        .orElse(null);

        System.out.println(book);
    }

    public void showDashboard() {

        System.out.println("\n===== DASHBOARD =====");

        System.out.println(
                "Total Books : "
                        + bookRepository.totalBooks()
        );

        System.out.println(
                "Total Users : "
                        + userRepository.totalUsers()
        );

        System.out.println(
                "Issued Books : "
                        + transactionRepository.totalIssuedBooks()
        );

        System.out.println(
                "Available Books : "
                        + bookRepository.availableBooks()
        );

        System.out.println(
                "Overdue Books : "
                        + transactionRepository.overdueBooks()
        );
    }

    public void showTransactionHistory() {

        transactionRepository
                .getTransactionHistory()
                .forEach(System.out::println);
    }

    private static final int FINE_PER_DAY = 5;

    public long calculateFine(LocalDate dueDate) {

        LocalDate today = LocalDate.now();

        if(today.isAfter(dueDate)) {

            long lateDays =
                    ChronoUnit.DAYS.between(
                            dueDate,
                            today
                    );

            return lateDays * FINE_PER_DAY;
        }

        return 0;
    }

    public void showFine(int transactionId) {

        Transaction tx =
                transactionRepository
                        .findTransactionById(
                                transactionId
                        );

        if(tx == null) {

            System.out.println(
                    "Transaction Not Found"
            );

            return;
        }

        long fine =
                calculateFine(
                        tx.getDueDate()
                );

        System.out.println(
                "Fine = ₹" + fine
        );
    }

    public void showOverdueBooks() {

        ArrayList<Transaction> overdueList =
                transactionRepository
                        .getOverdueTransactions();

        if(overdueList.isEmpty()) {

            System.out.println(
                    "No Overdue Books"
            );

            return;
        }

        for(Transaction tx : overdueList) {

            long fine =
                    calculateFine(
                            tx.getDueDate()
                    );

            System.out.println(
                    "Transaction ID : "
                            + tx.getTransactionId()
            );

            System.out.println(
                    "Book ID : "
                            + tx.getBookId()
            );

            System.out.println(
                    "User ID : "
                            + tx.getUserId()
            );

            System.out.println(
                    "Fine : ₹" + fine
            );

            System.out.println(
                    "---------------------"
            );
        }
    }

    public void searchBooksByAuthor(
            String author) {

        ArrayList<Book> books =
                bookRepository
                        .searchByAuthor(author);

        books.forEach(
                System.out::println
        );
    }

    public void showAvailableBooks() {

        bookRepository
                .getAvailableBooks()
                .forEach(System.out::println);
    }

    public void showOutOfStockBooks() {

        ArrayList<Book> books =
                bookRepository
                        .getOutOfStockBooks();

        if(books.isEmpty()) {

            System.out.println(
                    "No Out Of Stock Books"
            );

            return;
        }

        books.forEach(System.out::println);
    }

    public void sortBooksByTitleSQL() {

        bookRepository
                .sortBooksByTitle()
                .forEach(System.out::println);
    }

    public void sortBooksByQuantitySQL() {

        bookRepository
                .sortBooksByQuantity()
                .forEach(System.out::println);
    }


}
