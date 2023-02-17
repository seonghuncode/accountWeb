package TestDB;


import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import dao.TestRepository;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/web/WEB-INF/applicationContext.xml")
@Log4j
public class getListTest {

    @Setter
    private TestRepository testRepository;
    private DataSource dataSource;

    @Test
    public void getListTest(){
        try(Connection con = dataSource.getConnection()){
            log.info(testRepository.getList());
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}
