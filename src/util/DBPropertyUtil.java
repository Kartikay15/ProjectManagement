package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString(String propertyFileName) {
        Properties properties = new Properties();
        String connectionString = null;

        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + propertyFileName);
                return null;
            }

            // Load properties file
            properties.load(input);

            // Retrieve connection details
            String host = properties.getProperty("db.host");
            String port = properties.getProperty("db.port");
            String dbName = properties.getProperty("db.name");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Create connection string
            connectionString = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", host, port, dbName, username, password);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return connectionString;
    }
}
