package mapper;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/web/WEB-INF/applicationContext.xml")
@Log4j
public class testConnection {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection(){
        try(Connection con = dataSource.getConnection()){
            log.info(con);
            System.out.println(con);
        }
        catch(Exception e){
            System.out.println("연결실패");
            e.printStackTrace();

        }
    }
}
