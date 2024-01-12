package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util connect;

    public UserDaoJDBCImpl() {
        connect = new Util();
    }

    public void createUsersTable() {
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE users (
                      `id` INT PRIMARY KEY AUTO_INCREMENT,
                      `name` VARCHAR(30),
                      `lastname` VARCHAR(30),
                      `age` TINYINT(3))""");
            System.out.println("Table \"Users\" has been created");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table already exists");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE users");
            System.out.println("Table \"Users\" has been deleted");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table hasn't found");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("INSERT users (name, lastname, age) VALUES ('%s', '%s', %d)",
                                                    name, lastName, age));
            System.out.printf("User %s has been added to the database\n", name);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table hasn't found");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void removeUserById(long id) {
        String statementString = String.format("DELETE FROM users WHERE id = %d", id);
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            String result = statement.executeUpdate(statementString) == 1
                    ? "User %d has been removed\n"
                    : "ID %d has not found\n";
            System.out.printf(result, id);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table hasn't found");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(0);
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            resultSet.next();
            int size = resultSet.getInt(1);
            if (size == 0) {
                System.out.println("There are no users in the table");
            } else {
                userList = new ArrayList<>(size);
                resultSet = statement.executeQuery("SELECT * FROM users");
                System.out.println("List of users:");
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong(1));
                    user.setName(resultSet.getString(2));
                    user.setLastName(resultSet.getString(3));
                    user.setAge(resultSet.getByte(4));
                    userList.add(user);
                    System.out.println(user.toString());
                }
            }
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table hasn't found");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("Table \"Users\" has been cleaned");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("The \"Users\" table hasn't found");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
