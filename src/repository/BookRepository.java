package src.repository;

import src.model.Book;
import src.util.FileUtil;
import src.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookRepository {

    private static final String BOOK_FILE = "books.dat";

    public void saveBooks(ArrayList<Book> books) {

        FileUtil.saveObject(books, BOOK_FILE);
    }

    public void addBook(Book book) {

        String sql =
                "INSERT INTO books VALUES(?,?,?,?)";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, book.getId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getQuantity());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public ArrayList<Book> getAllBooks() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                "SELECT * FROM books";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return books;
    }

    public Book findBookById(int id) {

        String sql =
                "SELECT * FROM books WHERE id = ?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Book> loadBooks() {

        Object obj = FileUtil.loadObject(BOOK_FILE);

        if(obj != null) {
            return (ArrayList<Book>) obj;
        }

        return new ArrayList<>();
    }

    public void deleteBook(int id){

    }

    public Book findBookByTitle(String title) {

        String sql =
                "SELECT * FROM books WHERE title LIKE ?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%");

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void updateQuantity(int bookId, int quantity) {

        String sql =
                "UPDATE books SET quantity=? WHERE id=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, bookId);

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int totalBooks() {

        String sql =
                "SELECT COUNT(*) FROM books";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int availableBooks() {

        String sql =
                "SELECT COUNT(*) FROM books WHERE quantity > 0";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<Book> searchByAuthor(
            String author) {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM books
                WHERE author LIKE ?
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    "%" + author + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book>
    getAvailableBooks() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM books
                WHERE quantity > 0
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> getOutOfStockBooks() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM books
                WHERE quantity = 0
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> sortBooksByTitle() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM books
                ORDER BY title
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> sortBooksByQuantity() {

        ArrayList<Book> books =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM books
                ORDER BY quantity DESC
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("quantity")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return books;
    }
}