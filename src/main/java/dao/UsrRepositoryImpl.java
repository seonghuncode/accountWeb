package dao;

import dto.UsrDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void join(UsrDto usrDto){};

    public String getUserPassword(String userId){
        return  sqlSession.selectOne("dao.UsrRepositoryImpl.getUserPassword", userId);
    }

    public String findUserNameByUserId(String userId){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.findUserNameByUserId", userId);
    }

    public List<UsrDto> getAllUserFromDB(){ //DB에 있는 모든 회원을 List에 담아 return한다.
        return sqlSession.selectList("dao.UsrRepositoryImpl.getAllUserFromDB");
    }

}
