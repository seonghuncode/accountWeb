package TestDB;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/web/WEB-INF/applicationContext.xml")
@Log4j
public class testMyBatis {

    @Setter
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testMyBatis() {
        try(SqlSession session = sqlSessionFactory.openSession();
            Connection conn = session.getConnection();) {
            log.info(session);
            log.info(conn);
        } catch(Exception e) {
//            fail(e.getMessage());
            System.out.println("실패입니다");
        }
    }
}
