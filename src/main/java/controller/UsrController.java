package controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrController {

    @RequestMapping(value = "/")
    public String Main(){
        return "redirect:/usr/login";
    }


    @RequestMapping(value = "/usr/login")
    public String doJoin(Model model) {
        return "thymeleaf/usr/login";
    }


}
