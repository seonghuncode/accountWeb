package service;


import dao.UsrRepository;
import dto.UsrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsrDto getMemberByLoginId(String userId) {
        UsrDto result = usrRepository.getMemberByLoginId(userId);
        return result;
    }

    //이메일 중복 여부만 가지고 오는 로직
    public String getCheckExistEmail(String email) {
        String result = usrRepository.getCheckExistEmail(email);
        return result;
    }

    //아이디 중복 여부만 가지고 오는 로직
    public String getCheckExistUserId(String userId) {
        String result = usrRepository.getCheckExistUserId(userId);
        return result;
    }

    //회원가입을 진행하는 로직
    public void doJoin(UsrDto usrDto) {
        String encoderPassword = passwordEncoder.encode(usrDto.getPassword()); //비밀번호 암호화
//        System.out.println("암호화된 비밀번호");
//        System.out.println(encoderPassword);
        usrDto.setPassword(encoderPassword); //기존 비밀번호를 암호화 비밀번호로 변경
//        System.out.println("usrDto 보기");
//        System.out.println(usrDto);
        usrRepository.join(usrDto); //비밀번호 저장
    }

    //유효성 검사를 하고 존재하는 에러를 모두 bindingResult에 넣어 주고 에러가 없을 경우 성공 메세지를 return하도록 하는 중복 로직 함수
    //매개변수로 String successFn 받는이유 : 에러를 모아주는 기능은 로그인과 회원가입에서 사용하는데 로그인 요청에서 에러가 없으면 로그인 기능을 수행하고
    //회원가입 요청에서 에러가 없을 경우 회원가입 기능이 실행되도록 어떠한 요청인지 구분하기 위한 매개변수
    public Map<String, Object> errorProcess(BindingResult bindingResult, UsrDto usrDto, String successFn) {
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

            if(successFn == "join"){
                //회원가입 진행 로직(비밀번호 암호화 저장)
                doJoin(usrDto); //비밀번호 저장
            }else if(successFn == "login"){
                doLogin(usrDto);
            }
            createResult.put("success", 200);
        }
        return createResult;
    }

    //가입 하기 버튼을 클릭했을때 실행될 로직(비밀번호:일치여부, 특수문자 조합 및 길이 확인, 나머지 DTO에서 @Valid설정한 유효성 검사)
    public Map<String, Object> doCheckJoin(UsrDto usrDto, BindingResult bindingResult) {
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

        //이메일 중복 여부 체크(중복되는 값이 존재 하지 않을 경우는 이메일 형식 여부만 판별)
        if (getCheckExistEmail(usrDto.getEmail()) != null) {
            bindingResult.addError(new FieldError("usrDto", "email", "해당 이메일은 이미 존재하는 이메일 입니다."));
        }
        //가입시에도 기존 조건 외에 중복 여부 확인 (조건 : 입력?, 자릿수, 중복확인)
        if (getCheckExistUserId(usrDto.getUserId()) != null) {
            bindingResult.addError(new FieldError("usrDto", "userId", "해당 아이디는 이미 존재하는 아이디 입니다."));
        }

        //에러들을 모두 bindingResult에 담아주는 함수
        String successFn = "join";//회원가입 로직이라는 것을 errorProccess에 알려주기 위한 변수
        Map<String, Object> createResult = errorProcess(bindingResult, usrDto, successFn);

//        System.out.println("createResult");
//        System.out.println(createResult);
        return createResult;

    }

    //이메일 중복확인 버튼을 클릭했을때 실행될 로직
    public Map<String, Object> doCheckEmail(UsrDto usrDto, BindingResult bindingResult, String existEmail) {
        //이메일 중복 검사
//        String existEmail = usrService.getCheckExistEmail(usrDto.getEmail());
        //name에 값을 넣는 이유 : ajax에서 name은 이메일 중복 여부로 사용, email은 형식이 맞는지 확인하는 용도로 사용
        if (existEmail != null) { //중복확인시 이메일 형식이 아닐 수 있기 때문에 email에 값을 넣지 말고 name에 값을 넣는다.
            System.out.println("existEmail 실행");
            System.out.println(existEmail);
            bindingResult.addError(new FieldError("usrDto", "name", "해당 이메일은 이미 존재하는 이메일 입니다."));
        } else {
            bindingResult.addError(new FieldError("usrDto", "name", "해당 이메일은 사용 가능 합니다."));
        }


        String successFn = "join";
        //에러들을 모두 bindingResult에 담아주는 함수
        Map<String, Object> createResult = errorProcess(bindingResult, usrDto, successFn);

//        System.out.println("createResult");
//        System.out.println(createResult);
        return createResult;
    }

    //아이디 중복 확인 버튼을 클릭했을 경우 실행될 로직
    public Map<String, Object> doCheckUserId(UsrDto usrDto, BindingResult bindingResult) {
        String existUserId = getCheckExistUserId(usrDto.getUserId());

        if (existUserId != null) {
            bindingResult.addError(new FieldError("usrDto", "userId", "해당 아이디는 이미 존재하는 아이디 입니다."));
        } else {
            bindingResult.addError(new FieldError("usrDto", "userId", "해당 아이디는 사용 가능 합니다"));
        }
        String successFn = "join";
        Map<String, Object> createResult = errorProcess(bindingResult, usrDto, successFn);
        return createResult;
    }


    //로그인 진행을 위한 로직(1. 아이디 DB존재 여부, 해당 아이디랑 비밀번호 일치 하는지 확인)
    public Map<String, Object> doCheckLogin(UsrDto usrDto, BindingResult bindingResult){

        //현재 사용자가 입력한 아이디가  DB에 존재하지 않거나 존재할 경우 해당 아이디의 비밀번호가 다르면 문제 없다
        //입력한 아이디와 비밀번호가 일치하는지 비교 불가(평문이 같다고 암호화 까지 같지 않다?? 해독한 상택로 비교해야 한다??)

//        System.out.println("<<<>>>>>>>>");
//        System.out.println( usrRepository.getUserPassword(usrDto.getUserId()));
//        System.out.println(passwordEncoder.encode(usrDto.getPassword()));
//        System.out.println("<<<>>>>>>>>");

        //해당 아이디가 DB에 존재 하는지 확인
        String existUserId = usrRepository.getCheckExistUserId(usrDto.getUserId());
//        System.out.println("찾아온 아이디 : " + existUserId);
        if(existUserId == null){ //해당 아이디가 DB에 존재 하지 않을 경우
            bindingResult.addError(new FieldError("usrDto", "userId", "아이디 또는 비밀번호가 일치 하지 않습니다."));
        }else if(existUserId != null){  //해당 아이디가 DB에 존재 한다면 사용자가 입력한 비밀번호가 일치하는지 확인
            String findPasswordFromDB = usrRepository.getUserPassword(usrDto.getUserId()); //아이디를 파라미터로 넘기면 해당 아이디의 비밀번호를 찾아온다.
            String encoderPassword = passwordEncoder.encode(usrDto.getPassword()); //사용자가 입력한 비밀번호
            if((passwordEncoder.matches(usrDto.getPassword(), findPasswordFromDB)) == false){ //사용자가 입력한 아이디의 비밀번호 != 사용자가 입력한 비밀번호
                bindingResult.addError(new FieldError("usrDto", "password", "아이디 또는 비밀번호가 일치 하지 않습니다."));
            }
        }

        String successFn = "login";
        System.out.println("에러모음");
        System.out.println( errorProcess(bindingResult, usrDto, successFn));

        //에러만 모아주는 함수를 실행시켜 결과만 리턴
        return  errorProcess(bindingResult, usrDto, successFn);
    }

    //doCheckLogin로직에서 유효성 검사를 완료하고 에러가 없을 경우 로그인을 하는 함수
    public void doLogin(UsrDto usrDto){
        //로그인 기능 추가, 문구, 세션 확인
        System.out.println(usrDto.getUserId() + "님 로그인 되었습니다.");
    }


}
