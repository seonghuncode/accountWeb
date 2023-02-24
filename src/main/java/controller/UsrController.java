package controller;

import dto.UsrDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    //로그인 폼에서 사용자가 입력한 value값에 대해 성공, 실패 로직을 처리하는 controller
    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> doLogin(@RequestBody UsrDto usrDto, BindingResult bindingResult) {
        System.out.println("로그인 화면에서 넘겨 받은 값");
        System.out.println("아이디 : " + usrDto.getUserId()+ "비밀번호" + usrDto.getPassword());

        System.out.println("==============");
        System.out.println("로그인 로직 return값");
        System.out.println(usrService.doCheckLogin(usrDto, bindingResult));
        System.out.println("==============");

       
        return  usrService.doCheckLogin(usrDto, bindingResult);
    }


    //회원가입 폼으로 화면으로 이동 시키는 controller
    @RequestMapping("/usr/joinForm")
    public String showJoin() {
        return "thymeleaf/usr/join";
    }

    //회원가입 폼에서 받은 value값들에 대해 성공, 실패를 처리하는 로직의 controller
    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST}, produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> doJoin(@Validated @RequestBody UsrDto usrDto, BindingResult bindingResult
    ) throws Exception {

        //회원가입 폼에서 받아온 value값들을 확인용
        System.out.println("html에서 받아온 데이터");
        System.out.println(
                "유저 이름 : " + usrDto.getName() +
                        "유저 이메일 : " + usrDto.getEmail() +
                        "유저 아이디 : " + usrDto.getUserId() +
                        "비밀번호 : " + usrDto.getPassword() +
                        "비밀번호  확인용" + usrDto.getCheckPassword() +
                        "view_yn : " + usrDto.getView_yn()
        );

        //중복확인 버튼을 클릭할 경우 email만 받아오면 되기 때문에 이메일 중복확인 아라는 것을 알려주기 위해 사용하지 않는 userId에 중복확인을 넣어 보내준다.
        //ajax에서 데이터가 넘어 왔을대 해당 데이터가 어떤 버튼을 클릭하고 넘어온 데이터인지 구분하여 기능 수행
        if (usrDto.getUserId().equals("중복확인")) { //이메일 중복확인 버튼을 클릭했을때 실행될 로직
            String existEmail = usrService.getCheckExistEmail(usrDto.getEmail());
            return usrService.doCheckEmail(usrDto, bindingResult, existEmail);
        } else if (usrDto.getEmail().equals("중복확인")) {  //아이디 중복확인 버튼을 클릭했을 경우 실행될 로직
            return usrService.doCheckUserId(usrDto, bindingResult);
        } else {//가입 하기 버튼을 클릭했을때 실행될 로직(비밀번호:일치여부, 특수문자 조합 및 길이 확인, 이메일 중복, 아이디 중복, 나머지 DTO에서 @Valid설정한 유효성 검사) / 성공 상황
            return usrService.doCheckJoin(usrDto, bindingResult);
        }
    }






}
