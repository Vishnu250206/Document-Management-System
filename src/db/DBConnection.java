package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config/db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return null;
            }
            prop.load(input);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException ex) {
            System.out.println("Database connection failed: " + ex.getMessage());
        }
        return conn;
    }
}
