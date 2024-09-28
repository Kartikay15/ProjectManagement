package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() {
        String connectionString = DBPropertyUtil.getConnectionString("db.properties"); // Use your property file name

        try {
            // Explicitly register the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            assert connectionString != null;
            return DriverManager.getConnection(connectionString);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
