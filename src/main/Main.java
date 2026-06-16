package src.main;

import src.exception.BookNotFoundException;
import src.exception.BookUnavailableException;
import src.exception.UserNotFoundException;
import src.model.LoginUser;
import src.model.User;
import src.service.LibraryService;
import src.model.Book;
import src.service.LibraryServiceImpl;
import src.repository.LoginRepository;


import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryService library = new LibraryServiceImpl();

        LoginRepository loginRepository =
                new LoginRepository();

        System.out.println("===== LOGIN =====");

        System.out.print("Username : ");
        String username = sc.nextLine();

        System.out.print("Password : ");
        String password = sc.nextLine();

        LoginUser loginUser =
                loginRepository.authenticate(
                        username,
                        password
                );

        if(loginUser == null) {

            System.out.println(
                    "Invalid Credentials"
            );

            return;
        }

        System.out.println(
                "Welcome " +
                        loginUser.getUsername()
        );

        System.out.println(
                "Role : " +
                        loginUser.getRole()
        );

        while (true) {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Add User");
            System.out.println("8. View Users");
            System.out.println("9. Issue History");
            System.out.println("10. Library Statistics");
            System.out.println("11. Search Book By Title");
            System.out.println("12. Sort Books By Title");
            System.out.println("13. Sort Books By Quantity");
            System.out.println("14. Exit");
            System.out.println("15. Dashboard");
            System.out.println("16. Transaction History");
            System.out.println("17. Show Fine");
            System.out.println("18. Overdue Books");
            System.out.println("19. Search Book By Author");
            System.out.println("20. Show Available Books");
            System.out.println("21. Out Of Stock Books");
            System.out.println("22. Sort By Title (SQL)");
            System.out.println("23. Sort By Quantity (SQL)");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    if(!loginUser.getRole()
                            .equalsIgnoreCase("ADMIN")) {

                        System.out.println(
                                "Access Denied"
                        );

                        break;
                    }

                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();


                    Book book = new Book(id, title, author, quantity);

                    library.addBook(book);

                    break;

                case 2:

                    library.displayBooks();

                    break;

                case 3:

                    System.out.print("Enter Book ID: ");
                    id = sc.nextInt();

                    Book foundBook = library.searchBook(id);

                    if (foundBook == null)
                        System.out.println("Book Not Found");
                    else
                        System.out.println(foundBook);

                    break;

                case 4:

                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();

                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();

                    try {

                        library.issueBook(bookId, userId);

                    } catch (BookNotFoundException e) {

                        System.out.println(e.getMessage());

                    } catch (UserNotFoundException e) {

                        System.out.println(e.getMessage());

                    } catch (BookUnavailableException e) {

                        System.out.println(e.getMessage());

                    }

                    break;

                case 5:

                    System.out.print("Enter Transaction ID : ");
                    int transactionId1 = sc.nextInt();

                    System.out.print("Enter Book ID : ");
                    int bookId1 = sc.nextInt();

                    library.returnBook(
                            transactionId1,
                            bookId1
                    );

                    break;

                case 6:

                    System.out.print("Enter Book ID: ");
                    id = sc.nextInt();

                    library.deleteBook(id);

                    break;

                case 7:

                    System.out.print("Enter User Id : ");
                    int uid = sc.nextInt();

                    sc.nextLine(); // consume newline

                    System.out.print("Enter Name : ");
                    String name = sc.nextLine();

                    library.addUser(new User(uid, name));

                    break;

                case 8:

                    library.viewUsers();

                    break;

                case 9:

                    library.showTransactions();

                    break;

                case 10:

                    library.showLibraryStatistics();

                    break;

                case 11:

                    sc.nextLine();

                    System.out.print("Enter Title : ");

                    String title1 = sc.nextLine();

                    Book foundBook1 = library.searchBookByTitle(title1);

                    if(foundBook1 == null) {
                        System.out.println("Book Not Found");
                    }
                    else {
                        System.out.println(foundBook1);
                    }

                    break;

                case 12:

                    library.sortBooksByTitle();

                    break;

                case 13:

                    library.sortBooksByQuantity();

                    break;

                case 14:

                    System.out.println("Thank You!");
                    return;

                case 15:
                    library.showDashboard();

                    break;

                case 16:

                    library.showTransactionHistory();

                    break;
                case 17:

                    System.out.print("Enter Transaction ID : ");

                    int transactionId = sc.nextInt();

                    library.showFine(transactionId);

                    break;

                case 18:

                    library.showOverdueBooks();

                    break;

                case 19:

                    sc.nextLine();

                    System.out.print("Enter Author : ");

                    String authorName = sc.nextLine();

                    library.searchBooksByAuthor(authorName);

                    break;

                case 20:

                    library.showAvailableBooks();

                case 21:

                    library.showOutOfStockBooks();

                    break;

                case 22:

                    library.sortBooksByTitleSQL();

                    break;

                case 23:

                    library.sortBooksByQuantitySQL();

                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
