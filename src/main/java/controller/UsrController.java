package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UsrService;

import java.util.Map;

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

    //회원가입 폼 에서 사용자가 입력한 값을 받아와 다음 결과로 이동시켜줄 경로
    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST} , produces = "application/json; charset=utf8")
    @ResponseBody
    public String doJoin(@RequestParam Map<String, Object> map) {

        System.out.println("html에서 받아온 데이터");
        System.out.println(map);

//        Map<String, Object> result = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            List<FieldError> errors = bindingResult.getFieldErrors();
//            for (FieldError error : errors) {
//                result.put(error.getField(), error.getDefaultMessage());
//                System.out.println(result.get(error.getField()));
//            }
//        } else {
//            result.put("result", 200);
//        }


        //UsrDto usrDto = usrService.getMemberByLoginId();


        return map.toString();
    }
}
