package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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


//    //로그인 폼 에서 정보가 오면 해당 값에 대해 예외처리 및 결과 값 코드 작성 + 성공시 세션 설정
//    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
//    @ResponseBody
//    public String doLogin(@RequestParam(value = "loginId", required=false) String loginId, @RequestParam(value = "loginPw", required=false) String loginPw) {
//        String result = "id : " + loginId + "pw : " +  loginPw;
//        System.out.println("result입니다.");
//        System.out.println(result);
//        return  result;
//    }


    //vo로 받는 방법
    //로그인 폼 에서 정보가 오면 해당 값에 대해 예외처리 및 결과 값 코드 작성 + 성공시 세션 설정
//    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
//    @ResponseBody
//    public String doLogin(UsrDto usrDto){
//        String result = "id : " + usrDto.getUserId() + "pw : " +  usrDto.getPassword();
//        System.out.println("result입니다.");
//        System.out.println(result);
//        return  result;
//    }
    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public String doLogin(@RequestParam Map<String, Object> map) {
        String result = "id : " + map.get("userId") + "pw : " + map.get("password");
//        System.out.println("map : " + map.get("userId"));
        System.out.println("result입니다.");
        System.out.println(result);

        System.out.println(map.toString());


//        Map result2 = new HashMap<String, Object>();
//        result2.put("\"" + "test1" + "\"", "\"" + "test1" + "\"");
//        result2.put("\"" + "test2" + "\"", "\"" + "test2" + "\"");
//        System.out.println("result2"+result2);


        return map.toString();
    }


    //회원가입 폼으로 화면으로 이동 시키는 경로
    @RequestMapping("/usr/joinForm")
    public String showJoin() {
        return "thymeleaf/usr/join";
    }

    //회원가입 폼 에서 사용자가 입력한 값을 받아와 다음 결과로 이동시켜줄 경로
    @RequestMapping(value = "/usr/joinFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public String doJoin(String name, String email, String userId, String userPw1, String userPw2, String view_yn) {
        String result = "name = " + name +
                "email = " + email +
                "userId = " + userId +
                "userPw1 = " + userPw1 +
                "userPw2 = " + userPw2 +
                "view_yn = " + view_yn;
        return result;
    }
}
