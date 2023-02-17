package dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class TestRepository {

    @Autowired
    SqlSession sqlSession;

    public String getList(){

        return  sqlSession.selectOne("dao.TestRepository.getList");

    }

}
