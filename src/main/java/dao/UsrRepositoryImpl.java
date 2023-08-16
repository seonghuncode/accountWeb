package dao;

import dto.Criteria;
import dto.UserByFindPw;
import dto.UsrDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    //페이징 기능을 위한 역할
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectBoardList(Criteria cri) {
//        return (List<Map<String,Object>>)selectList("board.selectBoardList", cri);
        return sqlSession.selectList("dao.UsrRepositoryImpl.selectBoardList", cri);
    }

    public int countUsrListTotal(){
        return (Integer) sqlSession.selectOne("dao.UsrRepositoryImpl.countUsrListTotal");
    }

    public int getAllUserCnt(){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.getAllUserCnt");
    }

    public int getNoUserCnt(){
        return sqlSession.selectOne("dao.UserRepositoryImpl.getNoUserCnt");
    }
    
    //사용자가 입력한 검색어에 해당하는 회원들만 DB에서 불러오는 역할
    public List<Map<String, Object>> getUsersFromSearch( Criteria cri){
        return sqlSession.selectList("dao.UsrRepositoryImpl.getUsersFromSearch", cri);
    }

    public int countSearchUsrListTotal(String search){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.countSearchUsrListTotal", search);
    }

    public int checkEmailForModifyMyInfo(Map<String, Object> data){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.checkEmailForModifyMyInfo", data);
    }

    public int checkUserIdForModifyMyInfo(Map<String, Object> data){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.checkUserIdForModifyMyInfo", data);
    }

    public int doModifyUserInfo(Map<String, Object> data){
        return sqlSession.update("dao.UsrRepositoryImpl.doModifyUserInfo", data);
    }

    public Map<String, Object> findUserIdProcess(Map<String, Object> data){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.findUserIdProcess", data);
    }

    public Integer getUserPkByFindPw(Map<String, Object> data){
        return sqlSession.selectOne("dao.UsrRepositoryImpl.getUserPkByFindPw", data);
    }

    public Integer changePwToTemporaryPw(UserByFindPw info){
        return sqlSession.update("dao.UsrRepositoryImpl.changePwToTemporaryPw", info);
    }


    public Integer doInfoDelete(Map<String, Object> data){
        return  sqlSession.delete("dao.UsrRepositoryImpl.doInfoDelete", data);
    }

}
