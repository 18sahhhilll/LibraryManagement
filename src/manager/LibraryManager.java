package manager;
import db.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class LibraryManager {


    public void issueBook(int memberId, int bookId) {
    String checkAvailability = "SELECT available FROM books WHERE id = ?";
    String insertHistory = "INSERT INTO borrow_history (member_id, book_id, borrow_date) VALUES (?, ?, ?)";
    String updateBook = "UPDATE books SET available = FALSE WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement checkStmt = conn.prepareStatement(checkAvailability)) {


        checkStmt.setInt(1, bookId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getBoolean("available")) {
        
            try (PreparedStatement insertStmt = conn.prepareStatement(insertHistory);
                 PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {

                insertStmt.setInt(1, memberId);
                insertStmt.setInt(2, bookId);
                insertStmt.setDate(3, Date.valueOf(LocalDate.now()));

                insertStmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                System.out.println("Book issued successfully!");

            }
        } else {
            System.out.println("Book is not available for issuing.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void returnBook(int memberId, int bookId) {
    String updateHistory = "UPDATE borrow_history SET return_date = ? " +
                           "WHERE member_id = ? AND book_id = ? AND return_date IS NULL";
    String updateBook = "UPDATE books SET available = TRUE WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement updateHistoryStmt = conn.prepareStatement(updateHistory);
         PreparedStatement updateBookStmt = conn.prepareStatement(updateBook)) {


        updateHistoryStmt.setDate(1, Date.valueOf(LocalDate.now()));
        updateHistoryStmt.setInt(2, memberId);
        updateHistoryStmt.setInt(3, bookId);

        int rowsUpdated = updateHistoryStmt.executeUpdate();

        if (rowsUpdated > 0) {

            updateBookStmt.setInt(1, bookId);
            updateBookStmt.executeUpdate();

            System.out.println("Book returned successfully!");
        } else {
            System.out.println("No matching borrowed book found or already returned.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    

public void viewAllBooks() {
    String query = "SELECT * FROM books";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        System.out.println("\nList of Books:");
        System.out.println("--------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String isbn = rs.getString("isbn");
            String category = rs.getString("category");
            boolean available = rs.getBoolean("available");

            System.out.println("ID: " + id +
                               ", Title: " + title +
                               ", Author: " + author +
                               ", ISBN: " + isbn +
                               ", Category: " + category +
                               ", Available: " + (available ? "Yes" : "No"));
        }

        System.out.println("--------------------------------------------------");

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void registerMember(String name) {
    String query = "INSERT INTO members (name) VALUES (?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, name);

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Member registered successfully!");
        } else {
            System.out.println("Failed to register the member.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public void deleteBook(int bookId) {
    String deleteHistory = "DELETE FROM borrow_history WHERE book_id = ?";
    String deleteBook = "DELETE FROM books WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement historyStmt = conn.prepareStatement(deleteHistory);
         PreparedStatement bookStmt = conn.prepareStatement(deleteBook)) {

        
        historyStmt.setInt(1, bookId);
        historyStmt.executeUpdate();

       
        bookStmt.setInt(1, bookId);
        int rowsDeleted = bookStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Book and its borrow history deleted successfully!");
        } else {
            System.out.println("Book not found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



public void deleteMember(int memberId) {
    String deleteHistory = "DELETE FROM borrow_history WHERE member_id = ?";
    String deleteMember = "DELETE FROM members WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement historyStmt = conn.prepareStatement(deleteHistory);
         PreparedStatement memberStmt = conn.prepareStatement(deleteMember)) {


        historyStmt.setInt(1, memberId);
        historyStmt.executeUpdate();


        memberStmt.setInt(1, memberId);
        int rowsDeleted = memberStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Member and their borrow history deleted successfully!");
        } else {
            System.out.println("Member not found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void searchBookByTitle(String titleKeyword) {
    String query = "SELECT * FROM books WHERE title LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, "%" + titleKeyword + "%"); 
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n🔍 Search Results for: " + titleKeyword);
        System.out.println("--------------------------------------------------");

        boolean found = false;

        while (rs.next()) {
            found = true;
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String isbn = rs.getString("isbn");
            String category = rs.getString("category");
            boolean available = rs.getBoolean("available");

            System.out.println("ID: " + id +
                               ", Title: " + title +
                               ", Author: " + author +
                               ", ISBN: " + isbn +
                               ", Category: " + category +
                               ", Available: " + (available ? "Yes" : "No"));
        }

        if (!found) {
            System.out.println("No books found with that title.");
        }

        System.out.println("--------------------------------------------------");

    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public void addBook(String title, String author, String isbn, String category) {
        String query = "INSERT INTO books (title, author, isbn, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, category);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book added successfully!");
            } else {
                System.out.println("Failed to add the book.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
