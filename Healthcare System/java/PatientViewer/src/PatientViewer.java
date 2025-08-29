import java.sql.*;

public class PatientViewer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/healthcare_db";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT p.patient_id, u.full_name, u.email, p.gender, p.phone, p.address " +
                    "FROM patients p JOIN users u ON p.user_id = u.user_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s %-20s %-25s %-10s %-15s %-30s%n",
                    "ID", "Full Name", "Email", "Gender", "Phone", "Address");
            System.out.println("------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-25s %-10s %-15s %-30s%n",
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}

