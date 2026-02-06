import java.sql.*;

public class StudentCRUD {

    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "your_password_here";

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish Connection
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");

            // 3. INSERT record (PreparedStatement → prevents SQL Injection)
            String insertSQL = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
            ps = con.prepareStatement(insertSQL);
            ps.setString(1, "John Doe");
            ps.setString(2, "john@gmail.com");
            ps.setInt(3, 22);
            ps.executeUpdate();
            System.out.println("Record inserted.");

            // 4. RETRIEVE records
            String selectSQL = "SELECT * FROM students";
            ps = con.prepareStatement(selectSQL);
            rs = ps.executeQuery();

            System.out.println("\nStudent Records:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getInt("age")
                );
            }

            // 5. UPDATE record
            String updateSQL = "UPDATE students SET age = ? WHERE id = ?";
            ps = con.prepareStatement(updateSQL);
            ps.setInt(1, 23);
            ps.setInt(2, 1);
            ps.executeUpdate();
            System.out.println("\nRecord updated.");

            // 6. DELETE record
            String deleteSQL = "DELETE FROM students WHERE id = ?";
            ps = con.prepareStatement(deleteSQL);
            ps.setInt(1, 1);
            ps.executeUpdate();
            System.out.println("Record deleted.");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database error occurred!");
            e.printStackTrace();

        } finally {
            // 8. Close resources properly
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
                System.out.println("\nResources closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}