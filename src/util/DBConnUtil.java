package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/db"; // Your database name
        String username = "root"; // Hardcoded username
        String password = "root"; // Hardcoded password

        try {
            // Explicitly register the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
