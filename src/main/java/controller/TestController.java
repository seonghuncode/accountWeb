package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestService;
import vo.TestVo;

//@Configuration
//@ComponentScan({"repository"})


@Controller
public class TestController {

    /**
     *
     * @RequiredArgsConstructor
     *   private final TestService testService;
     */



//    @Autowired
//    private TestService testService;
//
    private TestService testService;
    public TestController(TestService testService){
        this.testService = testService;
    }





// private TestService testService;
// public TestController(TestService testService){
//     this.testService = testService;
// }


//    @RequestMapping(value="/")
//    public String test(){
//        return "jsp/test/index";
//    }

    @RequestMapping(value = "/test", produces = "application/json; charset=utf8")
    @ResponseBody
    public String test2() {
        return "test2 입니다.";
    }

    @RequestMapping("test3")
    public String test3() {
        return "jsp/test/test3";
    }

    //타임리프
    @RequestMapping(value = "/usr/test")
    public String doJoin(Model model) {
        model.addAttribute("greeting", "Success입니다.");
        return "thymeleaf/test/test3";
    }

    @RequestMapping(value ="/mybatis/test", produces = "application/json; charset=utf8")
    @ResponseBody
    //실제 데이터베이스 에서 mybatis를 통해 데이터 가지고 오는 테스트
    public String Test(TestVo testVo) {
        String result = testService.getList();
        System.out.println(result);
        return result;
    }
}
