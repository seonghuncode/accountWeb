package controller;

import java.sql.*;

public class TestJDBC {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/account_web";
        String userName = "ysh";
        String password = "ysh123";

        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from test");

        resultSet.next();
        String name = resultSet.getString("test_name");
        System.out.println(name);

        resultSet.close();
        statement.close();
        connection.close();

    }
}
