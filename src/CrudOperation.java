import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CrudOperation {

    private final static String URL = "jdbc:postgresql://localhost:5432/library_db";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "Kali";

    private final static Scanner SCANNER = new Scanner(System.in);

    public void createBooks() throws SQLException {
        System.out.println("Enter book title: ");
        String title = SCANNER.nextLine();
        System.out.println("Enter book author: ");
        String author = SCANNER.nextLine();

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, author);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add book.");
        }

        ps.close();
        conn.close();
    }

    public void findBookByTitle() throws SQLException {
        System.out.print("Enter book title to search: ");
        String title = SCANNER.nextLine();

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "SELECT * FROM books WHERE title ILIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + title + "%");

        ResultSet rs = ps.executeQuery();

        boolean found = false;
        while (rs.next()) {
            found = true;
            int id = rs.getInt("id");
            String bookTitle = rs.getString("title");
            String author = rs.getString("author");

            System.out.println("ID: " + id);
            System.out.println("Title: " + bookTitle);
            System.out.println("Author: " + author);
            System.out.println("---------------------------");
        }

        if (!found) {
            System.out.println("No book found with title: " + title);
        }

        rs.close();
        ps.close();
        conn.close();
    }

    public void updateBookById() throws SQLException {
        System.out.print("Enter book ID to update: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        // Check if the book with that ID exists
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String checkSql = "SELECT * FROM books WHERE id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, id);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            System.out.println("Book with ID " + id + " does not exist.");
            rs.close();
            checkStmt.close();
            conn.close();
            return; // stop execution
        }

        //Prompt user for new values
        System.out.print("Enter new title: ");
        String newTitle = SCANNER.nextLine();

        System.out.print("Enter new author: ");
        String newAuthor = SCANNER.nextLine();

        //Perform update
        String updateSql = "UPDATE books SET title = ?, author = ? WHERE id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        updateStmt.setString(1, newTitle);
        updateStmt.setString(2, newAuthor);
        updateStmt.setInt(3, id);

        int rowsAffected = updateStmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Failed to update book.");
        }

        //Clean up
        rs.close();
        checkStmt.close();
        updateStmt.close();
        conn.close();
    }

    public void deleteBookById() throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "DELETE FROM books WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }

        ps.close();
        conn.close();
    }

    public void listAllBooks() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "SELECT * FROM books ORDER BY id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("=== All Books ===");
        boolean hasResults = false;

        while (rs.next()) {
            hasResults = true;
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("-------------------");
        }

        if (!hasResults) {
            System.out.println("No books found.");
        }

        rs.close();
        ps.close();
        conn.close();
    }



}
