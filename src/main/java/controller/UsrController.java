package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrController {






    @RequestMapping(value = "/")
    public String Main() {
        return "redirect:/usr/loginForm";
    }


    //로그인 폼 화면으로 이동하는 코드
    @RequestMapping(value = "/usr/loginForm")
    public String showLogin() {
        return "thymeleaf/usr/login";
    }


    //로그인 폼 에서 정보가 오면 해당 값에 대해 예외처리 및 결과 값 코드 작성 + 성공시 세션 설정
    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8")
    @ResponseBody
    public String doLogin(String loginId, String loginPw) {
        return "로그인";
    }


    @RequestMapping("/usr/joinForm")
    public String showJoin() {
        return "thymeleaf/usr/join";
    }

    @RequestMapping("/usr/joinFn")
    public String doJoin() {
        return "thymeleaf/usr/join";
    }
}
