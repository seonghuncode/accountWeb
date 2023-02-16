package repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("TestRepositoryImpl")
public class TestRepositoryImpl implements TestRepository{


    @Autowired
    SqlSession sqlSession;



    @Override
    public String getList(){
        //return sqlSession.selectOne("web.resources.static.getList");  //추후 작성


        return  sqlSession.selectOne("repository.TestRepositoryImpl.getList");  //추후 작성




    }
}
