package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrController {

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


    //로그인 폼 에서 정보가 오면 해당 값에 대해 예외처리 및 결과 값 코드 작성 + 성공시 세션 설정
    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8")
    @ResponseBody
    public String doLogin(String loginId, String loginPw) {
        String result = "id : " + loginId + "pw : " +  loginPw;
        return  result;
    }


    //회원가입 폼으로 화면으로 이동 시키는 경로
    @RequestMapping("/usr/joinForm")
    public String showJoin() {
        return "thymeleaf/usr/join";
    }

    //회원가입 폼 에서 사용자가 입력한 값을 받아와 다음 결과로 이동시켜줄 경로
    @RequestMapping("/usr/joinFn")
    public String doJoin() {
        return "thymeleaf/usr/join";
    }
}
