package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(
                    """
                            CREATE TABLE IF NOT EXISTS `kata` (
                                `id` INT NOT NULL AUTO_INCREMENT,
                                `name` NVARCHAR(30),
                                `lastName` NVARCHAR(30),
                                `age` TINYINT(255),
                                PRIMARY KEY (`id`)
                            );
                            """
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute("DROP TABLE IF EXISTS kata");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `kata`(`name`,`lastName`,`age`)\n" +
                            "VALUES(?, ?, ?);");
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM kata WHERE id = ?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery("select * from kata;");
            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                result.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute("DELETE FROM kata WHERE id > -1;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
