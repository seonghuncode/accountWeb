package service;

import dto.UsrDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
public interface UsrService {
    public UsrDto getMemberByLoginId(String userId);

    public String getCheckExistEmail(String email);

    public Map<String, Object> doCheckJoin(UsrDto usrDto, BindingResult bindingResult);

    public Map<String, Object> doCheckEmail(UsrDto usrDto, BindingResult bindingResult, String existEmail);

    public Map<String, Object> doCheckUserId(UsrDto usrDto, BindingResult bindingResult);
    
    public Map<String, Object> doCheckLogin(UsrDto usrDto, BindingResult bindingResult);  //로그인 하는 로직
}
