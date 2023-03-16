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

    //DB에서 전체 회원수를 가지고 오는 메서드
    int countUsrListTotal();
}
