package service;

import dto.Criteria;
import dto.UsrDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public interface UsrService {
    public UsrDto getMemberByLoginId(String userId);

    public String getCheckExistEmail(String email);

    public Map<String, Object> doCheckJoin(UsrDto usrDto, BindingResult bindingResult);

    public Map<String, Object> doCheckEmail(UsrDto usrDto, BindingResult bindingResult, String existEmail);

    public Map<String, Object> doCheckUserId(UsrDto usrDto, BindingResult bindingResult);
    
    public Map<String, Object> doCheckLogin(UsrDto usrDto, BindingResult bindingResult, HttpSession httpSession);  //로그인 하는 로직

    public Map<String, Object> doLogout( HttpSession httpSession);

    public List<UsrDto> getAllUserFromDB();

    //페이징 기능을 하는 역할
    List<Map<String, Object>> selectBoardList(Criteria cri);

    //DB에서 전체 회원수를 가지고 오는 메서드(view_yn=yes인경우만)
    int countUsrListTotal();

    //DB전체 회원수
    int getAllUserCnt();
    
    //viewyn = no인 전체 회원의 수
    int getNoUserCnt();
    
    //사용자가 입력한 검색어에 대한 회원만 받아오는 역할
    List<Map<String, Object>> getUsersFromSearch(Criteria cri);

    //검색 회원에 대한 전체 수
    int countSearchUsrListTotal(String search);
    
    //회원정보 페이지 에서 사용자가 입력한 비밀번호가 회원의 아이디와 일치하는지 확인하는 로직
    Map<String, Object> checkPW(Map<String, Object> data);

    //회원정보 수정 페이지 에서 사용자가 수정하려고 하는 이메일이 중복되는 이메일인지 확인하는 로직
    Map<String, Object> checkEmailForModifyMyInfo(Map<String, Object> data);

    //회원정보 수정 페이지 에서 사용자가 수정하려고 하는 아이다가 중복되는 아이디 인지 확인하는 로직
    Map<String, Object> checkUserIdForModifyMyInfo(Map<String, Object> data);

    //회원정보 수정 페이지 에서 사용자가 입력한 모든 데이터가 유효성 검사를 통과한 데이터로 실제 데이터베이스에 데이터를 수정 반영하는 로직
    Map<String, Object> doModifyUserInfo(Map<String, Object> data);

    //사용자가 아이디 찾기 페이지 에서 아이디 찾기 버튼을 클릭했을 경우 실행되는 로직
    Map<String, Object> findUserIdProcess(Map<String, Object> data);

}
