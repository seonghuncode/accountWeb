package controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrController {

    @RequestMapping(value ="/usr/login")
    public String doJoin(Model model){
        model.addAttribute("greeting", "Success입니다.");
        return "thymeleaf/test3";
    }





}
