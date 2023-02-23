package controller;

import dto.UsrDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.UsrService;

import java.util.Map;

@Slf4j //log사용(로그를 남기는 것)
@Controller
public class UsrController {

    @Autowired
    private UsrService usrService;


    //메인 으로 들어오면 로그인 화면으로 보내도록 지정
    @RequestMapping(value = "/")
    public String Main() {
        return "redirect:/usr/loginForm";
    }


    //로그인 폼 화면으로 이동하는 경로
    @RequestMapping(value = "/usr/loginForm")
    public String showLogin() {
        return "thymeleaf/usr/login";
    }


    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public String doLogin(@RequestParam Map<String, Object> map) {
        String result = "id : " + map.get("userId") + "pw : " + map.get("password");
//        System.out.println("map : " + map.get("userId"));
        System.out.println("result입니다.");
        System.out.println(result);
        System.out.println(map.toString());


        return map.toString();
    }


    //회원가입 폼으로 화면으로 이동 시키는 경로
    @RequestMapping("/usr/joinForm")
    public String showJoin() {

        return "thymeleaf/usr/join";
    }


    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST}, produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> doJoin(@Validated @RequestBody UsrDto usrDto, BindingResult bindingResult
    ) throws Exception {


        System.out.println("html에서 받아온 데이터");
        System.out.println(
                "유저 이름 : " + usrDto.getName() +
                        "유저 이메일 : " + usrDto.getEmail() +
                        "유저 아이디 : " + usrDto.getUserId() +
                        "비밀번호 : " + usrDto.getPassword() +
                        "비밀번호  확인용" + usrDto.getCheckPassword() +
                        "view_yn : " + usrDto.getView_yn()
        );

//        if (true) { //이메일 중복확인 버튼을 클릭했을때 실행될 로직
//
//        } else if (true) {  //아이디 중복확인 버튼을 클릭했을 경우 실행될 로직
//
//        } else {//가입 하기 버튼을 클릭했을때 실행될 로직
//            return usrService.doCheckJoin(usrDto, bindingResult);
//        }
        return usrService.doCheckJoin(usrDto, bindingResult);

//        //비밀번호 일치 검사
//        if (!usrDto.getPassword().equals(usrDto.getCheckPassword())) {
//            bindingResult.addError(new FieldError("usrDto", "checkPassword", "비밀번호가 일치 하지 않습니다."));
//            bindingResult.addError(new FieldError("usrDto", "password", "비밀번호가 일치 하지 않습니다."));
//        } else if (usrDto.getPassword().equals(usrDto.getCheckPassword())) {
//            //비밀번호 영문과 특수문자 포함 8자 이상 검사(조건 : '숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 15자'까지 허용)
//            String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$";
//            Boolean tt = Pattern.matches(pwPattern, usrDto.getPassword());
//            if (tt == false) {
//                bindingResult.addError(new FieldError("usrDto", "password", "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요."));
//                bindingResult.addError(new FieldError("usrDto", "checkPassword", "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요."));
//            }
//        }
//
//
//        //이메일 중복 검사
////        String existEmail = usrService.getCheckExistEmail(usrDto.getEmail());
////        if (existEmail != null) {
////            System.out.println("existEmail 실행");
////            System.out.println(existEmail);
////            bindingResult.addError(new FieldError("usrDto", "email", "해당 이메일은 이미 존재하는 이메일 입니다."));
////        }
////
////
////        //아이디 중복 검사
//
//        //오류값을 createResult에 넣어 주기 위한 로직
//        Map<String, Object> createResult = new HashMap<>();
//
//        if (bindingResult.hasErrors()) {   // 파라미터(UsrDTO 등록 폼에서의 입력값)에 대한 유효성 검사 메세지가 있는 경우
//            //에러를 가지고 와서 list에 넣는다(해당 필드에 있는 메세지를 담아오겠다.)
//            List<FieldError> allErrors = bindingResult.getFieldErrors();
////            System.out.println("allErrors");
////            System.out.println(allErrors);
//            for (FieldError error : allErrors) {  //필드 에러만큼 반복문을 돌린다.
//                createResult.put(error.getField(), error.getDefaultMessage());
//            }
//        } else { //에러가 없을 경우 실행할 로직 + DB에 회원가입 저장
//            createResult.put("suceess", 200);
//        }
//        System.out.println("createResult");
//        System.out.println(createResult);
//        return createResult;


    }


}
