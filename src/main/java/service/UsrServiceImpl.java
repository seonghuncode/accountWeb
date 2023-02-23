package service;


import dao.UsrRepository;
import dto.UsrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UsrServiceImpl implements UsrService {

    @Autowired
    private UsrRepository usrRepository;

    public UsrDto getMemberByLoginId(String userId) {
        UsrDto result = usrRepository.getMemberByLoginId(userId);
        return result;
    }

    public String getCheckExistEmail(String email){
        String result = usrRepository.getCheckExistEmail(email);
        return result;
    }

    public Map<String, Object>  doCheckJoin(UsrDto usrDto, BindingResult bindingResult){
        //비밀번호 일치 검사
        if (!usrDto.getPassword().equals(usrDto.getCheckPassword())) {
            bindingResult.addError(new FieldError("usrDto", "checkPassword", "비밀번호가 일치 하지 않습니다."));
            bindingResult.addError(new FieldError("usrDto", "password", "비밀번호가 일치 하지 않습니다."));
        } else if (usrDto.getPassword().equals(usrDto.getCheckPassword())) {
            //비밀번호 영문과 특수문자 포함 8자 이상 검사(조건 : '숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 15자'까지 허용)
            String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$";
            Boolean tt = Pattern.matches(pwPattern, usrDto.getPassword());
            if (tt == false) {
                bindingResult.addError(new FieldError("usrDto", "password", "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요."));
                bindingResult.addError(new FieldError("usrDto", "checkPassword", "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요."));
            }
        }

        //오류값을 createResult에 넣어 주기 위한 로직
        Map<String, Object> createResult = new HashMap<>();

        if (bindingResult.hasErrors()) {   // 파라미터(UsrDTO 등록 폼에서의 입력값)에 대한 유효성 검사 메세지가 있는 경우
            //에러를 가지고 와서 list에 넣는다(해당 필드에 있는 메세지를 담아오겠다.)
            List<FieldError> allErrors = bindingResult.getFieldErrors();
//            System.out.println("allErrors");
//            System.out.println(allErrors);
            for (FieldError error : allErrors) {  //필드 에러만큼 반복문을 돌린다.
                createResult.put(error.getField(), error.getDefaultMessage());
            }
        } else { //에러가 없을 경우 실행할 로직 + DB에 회원가입 저장
            createResult.put("suceess", 200);
        }
        System.out.println("createResult");
        System.out.println(createResult);
        return createResult;

    }





}
