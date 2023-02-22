package controller;

import dto.UsrDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.UsrService;

import java.util.HashMap;
import java.util.List;
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

//    //회원가입 폼 에서 사용자가 입력한 값을 받아와 다음 결과로 이동시켜줄 경로
//    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST} , produces = "application/json; charset=utf8")
//    @ResponseBody
//    public String doJoin(@RequestParam Map<String, Object> map) {
//
//        System.out.println("html에서 받아온 데이터");
//        System.out.println(map);
//
////        Map<String, Object> result = new HashMap<>();
////        if (bindingResult.hasErrors()) {
////            List<FieldError> errors = bindingResult.getFieldErrors();
////            for (FieldError error : errors) {
////                result.put(error.getField(), error.getDefaultMessage());
////                System.out.println(result.get(error.getField()));
////            }
////        } else {
////            result.put("result", 200);
////        }
//
//
//        //UsrDto usrDto = usrService.getMemberByLoginId();
//
//        return map.toString();
//    }


    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST}, produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> doJoin(@Validated @RequestBody UsrDto usrDto, BindingResult bindingResult,
                                      HashMap<String, Object> param) throws Exception {


        System.out.println("html에서 받아온 데이터");
        System.out.println(
                "유저 이름 : " + usrDto.getName() +
                        "유저 이메일 : " + usrDto.getEmail() +
                        "유저 아이디 : " + usrDto.getUserId() +
                        "비밀번호 : " + usrDto.getPassword() +
                        "view_yn : " + usrDto.getView_yn()
        );

        //오류값을 createResult에 넣어
        Map<String, Object> createResult = new HashMap<>();

        if (bindingResult.hasErrors()) {   // 파라미터(UsrDTO 등록 폼에서의 입력값)에 대한 유효성 검사 메세지가 있는 경우
            //에러를 가지고 와서 list에 넣는다(해당 필드에 있는 메세지를 담아오겠다.)
            List<FieldError> allErrors = bindingResult.getFieldErrors();
//            System.out.println("allErrors");
//            System.out.println(allErrors);
            for (FieldError error : allErrors) {  //필드 에러만큼 반복문을 돌린다.
                createResult.put(error.getField(), error.getDefaultMessage());
            }
        } else { //에러가 없을 경우 실행할 로직
            createResult.put("suceess", 200);
        }
        System.out.println("createResult");
        System.out.println(createResult);
        return createResult;




        //return param;
    }


}
