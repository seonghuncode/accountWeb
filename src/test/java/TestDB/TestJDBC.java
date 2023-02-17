package TestDB;


import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/web/WEB-INF/applicationContext.xml")
@Log4j
public class TestJDBC {

    @Test
    public void TestJDBC() {
        String url = "jdbc:mysql://localhost:3306/account_web";
        String userName = "ysh";
        String password = "ysh123";

        //java.sql.connection 연결
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
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
        } catch (SQLException throwables) {
            System.out.println("JDBC  실패 입니다.");
            throwables.printStackTrace();
        }



    }
}
