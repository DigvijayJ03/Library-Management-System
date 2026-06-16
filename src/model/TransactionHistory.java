package src.model;

import java.time.LocalDate;

public class TransactionHistory {
    private String bookTitle;
    private String userName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String status;

    public TransactionHistory(
            String bookTitle,
            String userName,
            LocalDate issueDate,
            LocalDate dueDate,
            String status) {

        this.bookTitle = bookTitle;
        this.userName = userName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public String toString() {

        return "Book : " + bookTitle +
                "\nUser : " + userName +
                "\nIssue Date : " + issueDate +
                "\nDue Date : " + dueDate +
                "\nStatus : " + status +
                "\n--------------------";
    }
}
