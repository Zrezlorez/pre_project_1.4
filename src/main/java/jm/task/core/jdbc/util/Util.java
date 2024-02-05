package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String name = "kata";
    private static final String user = "root";
    private static final String password = "1234";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", name),  user, password);
    }
    // реализуйте настройку соеденения с БД
}
