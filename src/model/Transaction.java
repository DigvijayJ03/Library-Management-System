package src.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private LocalDate issueDate;
    private int bookId;
    private int userId;
    private int transactionId;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;



    public Transaction(
            int bookId,
            int userId,
            LocalDate issueDate) {

        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;

    }

    public Transaction(
            int transactionId,
            int bookId,
            int userId,
            LocalDate issueDate,
            LocalDate dueDate,
            LocalDate returnDate,
            String status) {

        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;


    }

    @Override
    public String toString() {

        return "Transaction ID : " + transactionId +
                ", Book ID : " + bookId +
                ", User ID : " + userId +
                ", Issue Date : " + issueDate +
                ", Due Date : " + dueDate +
                ", Return Date : " + returnDate +
                ", Status : " + status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }
}
