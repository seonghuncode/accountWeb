package Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping(value="/")
    public String test(){
        return "index";
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test2(){
        return "test2 입니다.";
    }

    @RequestMapping("test3")
    public String test3(){
        return "test3";
    }

}
