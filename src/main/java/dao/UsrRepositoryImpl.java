package dao;

import dto.UsrDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsrRepositoryImpl implements  UsrRepository {

    @Autowired
    SqlSession sqlSession;

    @Override
    public UsrDto getMemberByLoginId(String userId){
        return  sqlSession.selectOne("repository.TestRepositoryImpl.getList");
    }

    public String getCheckExistEmail(String email){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.getCheckExistEmail", email);
    }

    public String getCheckExistUserId(String userId){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.getCheckExistUserId", userId);
    }

}
