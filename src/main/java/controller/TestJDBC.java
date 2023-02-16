package controller;

import java.sql.*;

public class TestJDBC {


    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/account_web";
        String userName = "ysh";
        String password = "ysh123";

        //java.sql.connection 연결
        Connection connection = DriverManager.getConnection(url, userName, password);
        //sql에 담은 내용
        Statement statement = connection.createStatement();
        //sql요청 응답
        ResultSet resultSet = statement.executeQuery("select test_name from test where id=1");

        resultSet.next();
        String name = resultSet.getString("test_name");
        System.out.println(name);

        resultSet.close();
        statement.close();
        connection.close();


    }
}
