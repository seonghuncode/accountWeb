package service;


import dao.UsrRepository;
import dto.Criteria;
import dto.UsrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
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

            if (successFn == "join") {
                //회원가입 진행 로직(비밀번호 암호화 저장)
                doJoin(usrDto); //비밀번호 저장
            } else if (successFn == "login") {
                //System.out.println(usrDto.getName());
                createResult.put("name", usrDto.getName());
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
    public Map<String, Object> doCheckLogin(UsrDto usrDto, BindingResult bindingResult, HttpSession httpSession) {

        //현재 사용자가 입력한 아이디가  DB에 존재하지 않거나 존재할 경우 해당 아이디의 비밀번호가 다르면 문제 없다
        //입력한 아이디와 비밀번호가 일치하는지 비교 불가(평문이 같다고 암호화 까지 같지 않다?? 해독한 상택로 비교해야 한다??)

//        System.out.println("<<<>>>>>>>>");
//        System.out.println( usrRepository.getUserPassword(usrDto.getUserId()));
//        System.out.println(passwordEncoder.encode(usrDto.getPassword()));
//        System.out.println("<<<>>>>>>>>");

        //해당 아이디가 DB에 존재 하는지 확인
        String existUserId = usrRepository.getCheckExistUserId(usrDto.getUserId());
//        System.out.println("찾아온 아이디 : " + existUserId);

        boolean isLogined = false;
        if(httpSession.getAttribute("loginedUserId") !=  null){ //이미 세션이 존재 해서 로그인이 되어 있을 경우 isLoginedid = true로 변경
            isLogined = true;
        }
        if(isLogined){ //로그인을 진행할 경우 usrDto에 있는 변수명으로 bindingResult에 오류들을 모아 보내주는데 로그인 에서 사용하지 않는 checkPassword변수는
            bindingResult.addError(new FieldError("usrDto", "checkPassword", "이미 로그인이 되어 있습니다."));
            System.out.println("<<<<<<<<<<>>>>>>>>>>");
            System.out.println( httpSession.getAttribute("loginedUserId"));
            System.out.println("<<<<<<<<<<>>>>>>>>>>");
        }

        if (usrDto.getUserId().trim().length() == 0 && usrDto.getPassword().trim().length() == 0) {
            bindingResult.addError(new FieldError("usrDto", "userId", "아이디를 입력해 주세요."));
            bindingResult.addError(new FieldError("usrDto", "password", "비밀번호를 입력해 주세요."));
            bindingResult.addError(new FieldError("usrDto", "email", "미입력"));
        } else if (usrDto.getUserId().trim().length() == 0) {  //사용자가 값을 입력하지 않은 경우
            bindingResult.addError(new FieldError("usrDto", "userId", "아이디를 입력해 주세요."));
            //로그인을 할경우 email변수는 사용하지 않기 때문에 email을 통해 해당 오류는 데이터를 미입력했을 경우의 오류명 이라는 것을 알려주기 위해서 추가
            //프론트 에서 응답 받을때 단순히 userId or password라는 변수의 value값이 있으면 일치 하지 않는 오류로 판단, 추가적으로 email의 value값이 있으면 데이터 미입력 오류라는 것을 알기 위함
            bindingResult.addError(new FieldError("usrDto", "email", "미입력"));
        } else if (usrDto.getPassword().trim().length() == 0) {
            bindingResult.addError(new FieldError("usrDto", "password", "비밀번호를 입력해 주세요."));
            bindingResult.addError(new FieldError("usrDto", "email", "미입력"));
        } else if (existUserId == null) { //해당 아이디가 DB에 존재 하지 않을 경우
            bindingResult.addError(new FieldError("usrDto", "userId", "아이디 또는 비밀번호가 일치 하지 않습니다."));
        } else if (existUserId != null) {  //해당 아이디가 DB에 존재 한다면 사용자가 입력한 비밀번호가 일치하는지 확인
            String findPasswordFromDB = usrRepository.getUserPassword(usrDto.getUserId()); //아이디를 파라미터로 넘기면 해당 아이디의 비밀번호를 찾아온다.
            String encoderPassword = passwordEncoder.encode(usrDto.getPassword()); //사용자가 입력한 비밀번호
            if ((passwordEncoder.matches(usrDto.getPassword(), findPasswordFromDB)) == false) { //사용자가 입력한 아이디의 비밀번호 != 사용자가 입력한 비밀번호
                bindingResult.addError(new FieldError("usrDto", "password", "아이디 또는 비밀번호가 일치 하지 않습니다."));
            } else { //사용자가 입력한 아이디가 DB에 존재할 경우 해당 아이디에 대한 사용자 이름을 찾아 name에 넣어준다(성공시 환영 인사에 이름을 넣어 주기 위해서)
                usrDto.setName(usrRepository.findUserNameByUserId(usrDto.getUserId()));
            }
        }

//        System.out.println("아이디 길이 : " + usrDto.getUserId().trim().length());
        String successFn = "login"; //errorProcess에서 로그인애 대한 오류라는 것을 알려주기 위함
//        System.out.println("에러모음");
//        System.out.println(errorProcess(bindingResult, usrDto, successFn));

        Map<String, Object> loginStatus = errorProcess(bindingResult, usrDto, successFn);  //로그인이 성공적으로 진행되면 세션 등록
        if (loginStatus.containsKey("success") && loginStatus.containsValue(usrRepository.findUserNameByUserId(usrDto.getUserId()))){ //로그인이 성공적으로 됬을 경우 세션 등록
            httpSession.setAttribute("loginedUserId", usrDto.getUserId());
        }


        //에러만 모아주는 함수를 실행시켜 결과만 리턴
        return errorProcess(bindingResult, usrDto, successFn);
    }


    public Map<String, Object> doLogout(HttpSession httpSession){

        //String을 json형태로 return하기 위한 방법
        Map result = new HashMap<String, Object>();

        boolean isLoginedId = false;
        if(httpSession.getAttribute("loginedUserId") == null){
            isLoginedId = true;
        }
        //로그아웃이 되어 있다면 실질적으로 로그아웃 버튼을 클릭할 상황은 없지만 일단 만들어 놓음
        if(isLoginedId){
            System.out.println(httpSession.getAttribute("loginedUserId"));
            System.out.println("이미 로그아웃 되어 있습니다.");
            result.put("status", "이미 로그아웃 되어 있습니다.");
        }else{
            System.out.println(httpSession.getAttribute("loginedUserId"));
            httpSession.removeAttribute("loginedUserId");
            System.out.println("로그아웃 되었습니다.");
            result.put("status", "로그아웃 되었습니다.");
        }

        return result;

    }

    public List<UsrDto> getAllUserFromDB(){
        return usrRepository.getAllUserFromDB();
    }



    //페이징 기능을 위한 역할
    //BoardServiceImpl
    @Override
    public List<Map<String, Object>> selectBoardList(Criteria cri) {
        return usrRepository.selectBoardList(cri);
    }


    @Override
    public int countUsrListTotal(){
        return usrRepository.countUsrListTotal();
    }

    @Override
    public int getAllUserCnt(){
        return usrRepository.getAllUserCnt();
    }

    @Override
    public int getNoUserCnt(){
        return usrRepository.getNoUserCnt();
    }

    //메인화면에서 사용자가 입력한 검색어에 대한 회원만 불러오는 역할
    public List<Map<String, Object>> getUsersFromSearch(Criteria cri){
        return usrRepository.getUsersFromSearch(cri);
    }

    public int countSearchUsrListTotal(String search){
        return usrRepository.countSearchUsrListTotal(search);
    }

    //사용자가 마이페이지 에서 정보수정 버튼을 클릭해서 나오는 모달창에 입력한 비밀번호가 현재 로그인한 회원의 아이디와 일치하는지 확인하는 로직
    public Map<String, Object> checkPW(Map<String, Object> data){

        String userId = (String)data.get("userId");
        String password = (String)data.get("password");
//        System.out.println("사용자가 입력한 아이디(userId) : " + userId);
//        System.out.println("사용자가 입력한 비밀번호(password) : " + password);

        String findPasswordFromDB = usrRepository.getUserPassword(userId); //아이디를 파라미터로 넘기면 해당 아이디의 비밀번호를 찾아온다.
//        System.out.println("현재 아이디를 통해 데이터베이스 에서 매칭 되는 비빌번호를 찾아온다(findPassordFromDB) : " + findPasswordFromDB);
        String encoderPassword = passwordEncoder.encode(password); //사용자가 입력한 비밀번호

        int result2 = 1;
        if ((passwordEncoder.matches(password, findPasswordFromDB)) == false) { //사용자가 입력한 아이디의 비밀번호 != 사용자가 입력한 비밀번호
//            System.out.println("사용자가 입력한 비밀번호는 일치 하지 않습니다.");
            result2 = -1;
        }

        Map<String, Object> result = new HashMap<String, Object>();

        if(result2 == 1){
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;
    }


    //회원정보 수정 페이지 에서 사용자가 수정하려고 하는 이메일이 중복되는 이메일인지 확인하는 로직
    public Map<String, Object> checkEmailForModifyMyInfo(Map<String, Object> data){

        int result2 = usrRepository.checkEmailForModifyMyInfo(data);
//        System.out.println("중복 결과 : " + result2); //0이면 중복 없음, 1이면 중복

        Map<String, Object> result = new HashMap<String, Object>();

        if(result2 == 0){
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;

    }


    //회원정보 수정 페이지 에서 사용자가 수정하려고 하는 아이다가 중복되는 아이디 인지 확인하는 로직
    public Map<String, Object> checkUserIdForModifyMyInfo(Map<String, Object> data){

        int result2 = usrRepository.checkUserIdForModifyMyInfo(data);
//        System.out.println("중복 결과 : " + result2); //0이면 중복 없음, 1이면 중복

        Map<String, Object> result = new HashMap<String, Object>();

        if(result2 == 0){
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;

    }


    //회원정보 수정 페이지 에서 사용자가 입력한 모든 데이터가 유효성 검사를 통과한 데이터로 실제 데이터베이스에 데이터를 수정 반영하는 로직
    public Map<String, Object> doModifyUserInfo(Map<String, Object> data){

        String pw = (String)data.get("newPassword"); //사용자가 입력한 비밀번호 암호화
//        System.out.println("받은직후 비밀번호 : " + pw);
        if(!(pw.equals(""))){
            String encoderPassword = passwordEncoder.encode(pw); //비밀번호 암호화
            data.put("newPassword", encoderPassword); //암호화한 데이터 data객체에 넣기
        }else if(pw.equals("")){
            data.put("newPassword", "빈값");
        }
//        System.out.println("값이 있다면 암호화한 비빌번호 : " + (String)data.get("newPassword"));

        //사용자가 비밀번호를 입력했다면 해당 비밀번호로 수정하고
        //사용자가 비밀번호를 입력하지 않았다면 비밀번호는 수정하지 않는다.
        int result2 = usrRepository.doModifyUserInfo(data);
//        System.out.println("중복 결과 : " + result2); //1이면 숭정 성공, 0이면 수정 실패

        Map<String, Object> result = new HashMap<String, Object>();

        if(result2 == 1){
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;

    }




}
