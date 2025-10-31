package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    private final String url = "jdbc:postgresql://localhost:5432/MyClient_db";
    private final String user = "postgres";
    private final String password = "art554499";

    private DatabaseConnection() {
        initializeDatabase();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS clients (
                    clientId SERIAL PRIMARY KEY,
                    organizationName VARCHAR(100) NOT NULL,
                    typeProperty VARCHAR(50),
                    address VARCHAR(255),
                    telephone VARCHAR(20),
                    contactPerson VARCHAR(100)
                )
            """;

            stmt.executeUpdate(sql);
            System.out.println("Таблица 'clients' проверена или создана.");

        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации базы данных:");
            e.printStackTrace();
        }
    }
}
