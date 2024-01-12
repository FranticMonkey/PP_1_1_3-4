package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.Scanner;

public class Util {
    private final String username = "st_admin";
    private final String password = "st_admin";
/*
    public Util () {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username:");
        username = scan.nextLine();
        System.out.println("Enter password:");
        password = scan.nextLine();
        try (Connection connection = getConnection()) {
            System.out.println("Successful login");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
*/
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/for_pp", username, password);
    }
}
