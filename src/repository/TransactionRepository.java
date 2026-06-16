package src.repository;

import src.model.Transaction;
import src.util.DBConnection;
import src.util.FileUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import src.model.TransactionHistory;

public class TransactionRepository {


    private static final String TX_FILE = "transactions.dat";

    public void saveTransactions(ArrayList<Transaction> transactions) {

        FileUtil.saveObject(transactions, TX_FILE);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Transaction> loadTransactions() {

        Object obj = FileUtil.loadObject(TX_FILE);

        if(obj != null) {
            return (ArrayList<Transaction>) obj;
        }

        return new ArrayList<>();
    }



    public void addTransaction(
            int bookId,
            int userId,
            LocalDate issueDate) {

        String sql =
                """
                INSERT INTO transactions
                (book_id,user_id,issue_date,due_date,status)
                VALUES(?,?,?,?,?)
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            LocalDate dueDate =
                    issueDate.plusDays(14);

            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setDate(3,
                    java.sql.Date.valueOf(issueDate));
            ps.setDate(4,
                    java.sql.Date.valueOf(dueDate));
            ps.setString(5, "ISSUED");

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Transaction> getAllTransactions() {

        ArrayList<Transaction> transactions =
                new ArrayList<>();

        String sql =
                "SELECT * FROM transactions";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                transactions.add(
                        new Transaction(
                                rs.getInt("transaction_id"),
                                rs.getInt("book_id"),
                                rs.getInt("user_id"),
                                rs.getDate("issue_date")
                                        .toLocalDate(),

                                rs.getDate("due_date") != null
                                        ? rs.getDate("due_date")
                                        .toLocalDate()
                                        : null,

                                rs.getDate("return_date") != null
                                        ? rs.getDate("return_date")
                                        .toLocalDate()
                                        : null,

                                rs.getString("status")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return transactions;
    }

    public int totalIssuedBooks() {

        String sql =
                "SELECT COUNT(*) FROM transactions WHERE status='ISSUED'";

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

    public int overdueBooks() {

        String sql =
                """
                SELECT COUNT(*)
                FROM transactions
                WHERE status='ISSUED'
                AND due_date < CURDATE()
                """;

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

    public ArrayList<TransactionHistory> getTransactionHistory() {

        ArrayList<TransactionHistory> history =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    b.title,
                    u.name,
                    t.issue_date,
                    t.due_date,
                    t.status
                FROM transactions t
                JOIN books b
                    ON t.book_id = b.id
                JOIN users u
                    ON t.user_id = u.user_id
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                history.add(
                        new TransactionHistory(
                                rs.getString("title"),
                                rs.getString("name"),
                                rs.getDate("issue_date")
                                        .toLocalDate(),
                                rs.getDate("due_date")
                                        .toLocalDate(),
                                rs.getString("status")
                        )
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return history;
    }

    public Transaction findTransactionById(
            int transactionId) {

        String sql =
                "SELECT * FROM transactions WHERE transaction_id = ?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, transactionId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ?
                                rs.getDate("return_date")
                                        .toLocalDate()
                                : null,
                        rs.getString("status")
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Transaction>
    getOverdueTransactions() {

        ArrayList<Transaction> list =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM transactions
                WHERE status='ISSUED'
                AND due_date < CURDATE()
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                list.add(
                        new Transaction(
                                rs.getInt("transaction_id"),
                                rs.getInt("book_id"),
                                rs.getInt("user_id"),
                                rs.getDate("issue_date")
                                        .toLocalDate(),
                                rs.getDate("due_date")
                                        .toLocalDate(),
                                rs.getDate("return_date") != null ?
                                        rs.getDate("return_date")
                                                .toLocalDate()
                                        : null,
                                rs.getString("status")
                        )
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void returnBook(
            int transactionId) {

        String sql =
                """
                UPDATE transactions
                SET status='RETURNED',
                    return_date=CURDATE()
                WHERE transaction_id=?
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, transactionId);

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }



}