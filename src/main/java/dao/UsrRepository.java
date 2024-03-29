package dao;

import dto.Criteria;
import dto.UserByFindPw;
import dto.UsrDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UsrRepository {
    public UsrDto getMemberByLoginId(String userId);

    public String getCheckExistEmail(String email);

    public String getCheckExistUserId(String userId);

    public void join(UsrDto usrDto);

    public String getUserPassword(String userId); //아이디를 넘겨주면 해당 아이디의 비밀번호를 찾아서 넘겨준다.

    public String findUserNameByUserId(String userId);

    public List<UsrDto> getAllUserFromDB();
    
    //페이징 기능을 위한 역할
    public List<Map<String, Object>> selectBoardList(Criteria cri);

    public int countUsrListTotal();  //view

    public int getAllUserCnt();

    int getNoUserCnt();
    
    
    //사용자가 입력한 검색어에 대한 회원 정보만 받아오는 역할
    public List<Map<String, Object>> getUsersFromSearch(Criteria cri);

    int countSearchUsrListTotal(String search);

    int checkEmailForModifyMyInfo(Map<String, Object> data);

    int checkUserIdForModifyMyInfo(Map<String, Object> data);

    int doModifyUserInfo(Map<String, Object> data);

    Map<String, Object> findUserIdProcess(Map<String, Object> data);

    Integer getUserPkByFindPw(Map<String, Object> data);

    Integer changePwToTemporaryPw(UserByFindPw info);

    Integer doInfoDelete(Map<String, Object> data);
}
