package controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.TransactionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Getter
    @Setter
    //마이바티스에 매개변수로 넘겨주어야 하는 값이 2개이상 이기 때문에 객체에 담아 한번에 넘겨주는 방식
    //메인페이지 에서 사용자가 특정 회원을 클릭하면 해당 회원에 대한 정보를 갖고 있어야 조인해서 데이터를 찾을 수 있따.
    public class Transaction {
        int primaryId;
        String userId;
        String month;
    }


    @Autowired
    private TransactionService transactionService;


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

        //특정 회원의 userId를 통해 primary key로 되어있는 id를 찾아오는 로직
        //특정 페이지 에서 특정 회원 버튼을 클릭하면 해당 회원의 userId를 함께 넘겨준다 -> userId를 통해 primary key를 찾는다. -> join하기 위해서
        Transaction transaction = new Transaction();

        int primaryId = transactionService.getPrimaryId(userId);
        transaction.setPrimaryId(primaryId);
        //System.out.println("프리마리키 아이디" + primaryId);

        //검색월 기준에서 목표 예산을 불러오는 부분(당월이 default이기 때문에 당월에 대한 데이터를 넣어준다.)
        //조건 : 선택한 특정 회원 + 당월 ==> 예산액
        transaction.setMonth(changeMonth);
        transaction.setUserId(userId);
        //Integer로 받는 이유 : int의 경우 특정 사용자가 예산액을 지정하지 않은 경우 null값을 받을 수 없기 때문
        Integer targetBuget = transactionService.getTargetBudget(transaction);
//        System.out.println(targetBuget);
        model.addAttribute("targetBudget", targetBuget);

        //검색월 기준 현황 에서 남은 목표예산액, 수입, 지출에 대한 데이터를 보여주기 위해서 DB의 transaction 테이블에서 데이터를 가지고 오는 부분
        //transaction을 매개변수로 넘겨주는 이유는 transaction.primaryId를 조인할때 사용하기 위해 넘겨준다.
        List<Map<String, Object>> transactionValue = transactionService.getTransactionValue(transaction); //현재 받아온 데이터는 map형태 -> key,value로 구성 되어 있다.
        int incomeSum = 0; //수입 총액
        int expendSum = 0; //지출 총액
        for (Map<String, Object> item : transactionValue) {
            if (item.get("type").equals("지출")) {
                expendSum += (int) item.get("price");
            } else if (item.get("type").equals("수입")) {
                incomeSum += (int) item.get("price");
            }
        }

//        System.out.println(targetBuget);
        Integer leftMoney = -1;
        if (targetBuget != null) { //사용자가 예산값을 지정해 두어 null값이 아니라면
            leftMoney = targetBuget - expendSum; //월 목표 예산에서 - 월 총 소비
        }
        model.addAttribute("incomeSum", incomeSum);
        model.addAttribute("expendSum", expendSum);
        model.addAttribute("leftMoney", leftMoney);
//        System.out.println("지출 : " + expendSum);
//        System.out.println("소비 : " + incomeSum);
//        System.out.println(transactionValue);


        //지출 내역에 대한 전체 데이터를 불러오는 부분(transaction table 이랑 sort테이블 조인 + user테이블과 조인)
        List<Map<String, Object>> transactionHistory = transactionService.getTransactionHistory(transaction);
        model.addAttribute("transactionHistory", transactionHistory);
//        System.out.println(transactionHistory);
        //DB에서 날짜 별로 테이블을 생성하기 위해서는 반복문을 돌릴때 기존에 특정 날짜가 생성이 되었다면 해당 날짜의 테이블은 또 만들면 안된다
        //기존 jsp를 사용할 경우 최 상단에 set으로 변수를 선언 해서 하단 에서 한번 사용되면 해당 날짜를 set으로 해당 날짜를 저당해해주 었는데 thymeleaf의 경우 최상단에서 변수를 선언하면 하단에서 수정이 불가능 하고 사용만 가능하다
        //==> 중복된 날짜를 제거한 데이터 / 구현하는 로직의 경우 serviceImpl class에 구현을 하고 controller에서는 불러오기만 하기 위해 코드 분리
        List<Map<String, Object>> distinctTransactionHistory = new ArrayList<Map<String, Object>>();
        distinctTransactionHistory = transactionService.getDistinctTransactionHistory(transactionHistory);
        model.addAttribute("distinctTransactionHistory", distinctTransactionHistory);

        //일별 총 합계 수입, 총 하계 지출 내역에 대한 정보를 받아오는 로직
        List<Map<String, Object>> dayCntExpend = transactionService.getDayCntExpend(transaction); //일별 총 지출 내역 합계
//        System.out.println(dayCntExpend);
//        model.addAttribute("dayCntExpend",dayCntExpend);
        List<Map<String, Object>> dayCntIncome = transactionService.getDayCntIncome(transaction); //일별 총 수입 내역 합계
//        model.addAttribute("dayCntIncome", dayCntIncome);
//        System.out.println(dayCntIncome);

        //기존 특정 메인 페이지 에서 테이블을 일별로 보여주기 위한 데이터가 날짜, 일별 지출 총 합, 일별 수입 총 합으로 나누어져  따로 불러와서 사용하는데 어려움이 있어
        //==>3개의 데이터를 하나의 map List로 합치는 작업을 해주었다.
        //==>특정 회원의 지출 내역을 볼때 테이블을 입렬로 나타낼 경우 일별 날짜, 총 지출 합계, 총 수입 합계를 하나의 객체에 담아 클라이언트로 보내야 한다
        List<Map<String,Object>> getDailyTotalData = transactionService.getDailyTotalData(distinctTransactionHistory, dayCntExpend, dayCntIncome);
//        model.addAttribute("combinedList",getDailyTotalData);
//        System.out.println("============");
//        System.out.println(getDailyTotalData);
//        System.out.println("============");
        model.addAttribute("getDailyTotalData", getDailyTotalData);


        //DB에서 메인화면에서 클릭한 특정 회원의 아이디를 통해 -> 해당 회원의 목표 예산, 남은 목표예산, 수입 합계, 지출 합계를 받아와 넘겨준다.
        //1. userId에 대한 primary key값을 찾는다.
        //2. 예산액 테이블에서 외래키가 primary key값인 값을 찾아온다.
        //3. 거래내역 테이블에서 외래키가 primary key와 같으면서 type이 수입 / 지출인 것을 모두 각각 불러와 합해준다.
        //4. 전체 예산에서 지출 총 합계를 빼준다.
        //(transaction에 대한 service, repository를 다시 만들어 주기 위해서는 dispathcer-servlet에 등록을 해주어야 한다.)


        return "thymeleaf/content/otherTransaction";
    }


}
