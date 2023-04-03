package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;


@Controller
@RequestMapping("/transaction")
public class TransactionController {

    //{/usr/showTransaction(userId=${user.userId})}"
    @RequestMapping("/showTransaction")
    public String showTransaction(Model model, String userId) {

        //현재 날짜 구하는 부분
        LocalDate now = LocalDate.now();
        int year = now.getYear(); //ex. 2023
        String changeYear = String.valueOf(year).substring(2); //ex. 23

        int month = now.getMonthValue();  //ex. 3
        String changeMonth = String.format("%02d", month); //ex. 03

        model.addAttribute("year", changeYear);
        model.addAttribute("month", changeMonth);

        //DB에서 메인화면에서 클릭한 특정 회원의 아이디를 통해 -> 해당 회원의 목표 예산, 남은 목표예산, 수입 합계, 지출 합계를 받아와 넘겨준다.
        //1. userId에 대한 primary key값을 찾는다.
        //2. 예산액 테이블에서 외래키가 primary key값인 값을 찾아온다.
        //3. 거래내역 테이블에서 외래키가 primary key와 같으면서 type이 수입 / 지출인 것을 모두 각각 불러와 합해준다.
        //4. 전체 예산에서 지출 총 합계를 빼준다.
        //(transaction에 대한 service, repository를 다시 만들어 주기 위해서는 dispathcer-servlet에 등록을 해주어야 한다.)

        return "thymeleaf/content/otherTransaction";
    }


}
