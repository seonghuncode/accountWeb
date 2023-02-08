package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {


//    @RequestMapping(value="/")
//    public String test(){
//        return "jsp/test/index";
//    }

    @RequestMapping(value = "/test", produces = "application/json; charset=utf8")
    @ResponseBody
    public String test2(){
        return "test2 입니다.";
    }

    @RequestMapping("test3")
    public String test3(){
        return "jsp/test/test3";
    }

    //타임리프
    @RequestMapping(value ="/usr/test")
    public String doJoin(Model model){
        model.addAttribute("greeting", "Success입니다.");
        return "thymeleaf/test/test3";
    }





}
