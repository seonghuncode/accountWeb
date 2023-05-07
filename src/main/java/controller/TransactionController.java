package controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TransactionService;

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
        int primaryId; //현재 지출 내역의 회원 아이디의 primary key
        String userId; //현재 지출 내역의 회원 아이디
        String year; //현재 연도
        String month; //현재 월
        String selectYear; //사용자가 radio button에서 선택한 연도
        String selectMonth; //사용자가 radio button에서 선택한 월
        String sortName; //사용자가 입력한 분류명을 담는 변수
    }


    @Autowired
    private TransactionService transactionService;


    //{/usr/showTransaction(userId=${user.userId})}"
    //메인 화면에서 특정 회원을 클릭할 경우 해당 회원의 아이디를 매개변수로 받아와 해당 회원의 지출 내역을 보여주는 controller
    @RequestMapping("/showTransaction")
    public String showTransaction(Model model, String userId) {

        //현재 날짜 구하는 부분
        String changeYear = transactionService.getThisYear();
        String changeMonth = transactionService.getThisMonth();

        model.addAttribute("year", changeYear);
        model.addAttribute("month", changeMonth);

        //특정 회원의 userId를 통해 primary key로 되어있는 id를 찾아오는 로직
        //특정 페이지 에서 특정 회원 버튼을 클릭하면 해당 회원의 userId를 함께 넘겨준다 -> userId를 통해 primary key를 찾는다. -> join하기 위해서
        Transaction transaction = new Transaction();

        int primaryId = transactionService.getPrimaryId(userId);
        transaction.setPrimaryId(primaryId);
//        System.out.println("프리마리키 아이디" + primaryId);

        //검색월 기준에서 목표 예산을 불러오는 부분(당월이 default이기 때문에 당월에 대한 데이터를 넣어준다.)
        //조건 : 선택한 특정 회원 + 당월 ==> 예산액
        transaction.setYear(changeYear);
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
//        System.out.println("====================");
//        System.out.println(transaction.getMonth());
//        System.out.println(transaction.getYear());
//        System.out.println(transaction.getPrimaryId());
//        System.out.println("====================");
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
        //들어있는 데이터 예시 : [{transaction_date=2023-04-22, dayCntExpend=13500, dayCntIncome=8000}, {transaction_date=2023-04-21, dayCntExpend=13500, dayCntIncome=8000}, {transaction_date=2021-04-29, dayCntExpend=40000, dayCntIncome=40000}, {transaction_date=2021-04-22, dayCntExpend=0, dayCntIncome=16000}, {transaction_date=2021-04-16, dayCntExpend=40000, dayCntIncome=40000}]
        model.addAttribute("getDailyTotalData", getDailyTotalData);


        //현재 메인화면 -> 특정 회원의 지출 내역에 들어갈 경우 존재하는 일 수를 전부 보여준다(문제 : 데이터가 많을 경우 페이지가 스크롤 양이 늘어나 가독성이 떨어진다.)
        //해결 방안 : transactionService.getDateCnt(getDailyTotalData)
        //전체 DB에 존재 하는 일수를 구해서 일수가 3보다 작다면 가지고 있는 전체 데이터를 클라이언트로 보낸다.
        //만약 전체 일수가 3을 넘는다면 클라인언트로 3을 return해준다.
        //이후 클라이언트 에서 7일치 더보기 버튼을 클릭할 경우 ajax를 통해 /showTransaction/dateCnt와 통신해 최대 보여줄 일수를 return받아 클라이언트 수정
        int dateCnt = transactionService.getDateCnt(getDailyTotalData);
        model.addAttribute("dateCnt", dateCnt);


        //특정 회원의 지출 내역 화면 에서 당월 radio button을 선택할 경우 이페이지로 회원의 userId를 같이 넘겨 주기 위해서 보내주어야 한다.
        model.addAttribute("userId", userId);


//        //targetBuget
//        System.out.println("targetBuget : " + targetBuget);
//        //leftMoney
//        System.out.println("leftMoney : " + leftMoney);
//        //incomeSum
//        System.out.println("incomeSum : " + incomeSum);
//        //expendSum
//        System.out.println("expendSum : " + expendSum);
//        //getDailyTotalData
//        System.out.println("getDailyTotalData : " + getDailyTotalData);
//        //dateCnt
//        System.out.println("dateCnt : " + dateCnt);
//        //transactionHistory
//        System.out.println("transactionHistory : " + transactionHistory);
//        //transactionValue
//        System.out.println("transactionValue : " +  transactionValue);


        return "thymeleaf/content/otherTransaction";
    }


    //메인 페이지 에서 특정 회원을 클릭해서 특정회원의 거래내역 페이지로 들어가면 처음 최대 3일치의 데이터를 테이블로 보여준다.
    //이때 하단의 7일치 내역 더보기를 클릭 하면 가지고 있는 데이터에 7일치를 추가적으로 보여주기위해 +7을 해주는 로직
    // (이때 데이터 전체 일수의 갯수 < +3을 해준 값이 클경우 반복문의 마지막 조건을 데이터 일수의 전체 갯수로 변경을 해준다.)
    @RequestMapping(value = "/showTransaction/dateCnt", produces = "application/json; charset=utf8", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> dateCnt(@RequestParam int dateCnt) {

//        System.out.println(dateCnt);
        Map<String, Object> map = transactionService.getDateCnt2(dateCnt);

        return map;
    }



    //특정 회원 지출 내역 에서 사용자가 월별검색, 기간별 검색, 이번달 중 선택한 radio button에 따라 해당 data만 DB에서 모아 return해주는 작업을 해준다.
    @RequestMapping(value = "/showTransaction/whichSelect")
//    public String whichSelect(@RequestParam Map<String, Object> param, Model model){
    public String whichSelect(Model model, String userId, String selectYear, String selectMonth, String typeRadio, String sortName){

//        //사용자가 radio button중 월별 검색을 클릭하고 요청하는 경우 받는 데이터
//        String userId = (String) param.get("userId");
//        String selectYear = (String) param.get("year");
//        String selectMonth = (String) param.get("month");

        //Parameter로 받아야 하는데이터
        //userId + 선택한 연도, 선택한 원 or 시작(연도,월,일), 종료(년,월,일) + 현재 선택된 radio button 종류 + 검색한 분류명

        //thymeleaf/content/otherTransaction 해당 페이지로 보내주어야 하는 데이터>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //year, month, targetBuget, leftMoney, incomeSum, expendSum, getDailyTotalData, dateCnt, transactionHistory

        //추후 DB에 접근하여 데이터를 얻어올 경우 join을 할때 해당 userId릐 primary key를 찾기 위함
        Transaction transaction = new Transaction();
        int primaryId = transactionService.getPrimaryId(userId);
        transaction.setPrimaryId(primaryId);

        //year
        model.addAttribute("year", String.valueOf(selectYear).substring(2));
        //month
        model.addAttribute("month", selectMonth);

        //transaction객체에 DB에 접근해서 조건을 줄때 필요한 해당 userId, Year, Month값을 넣어 두어야 한다.
        transaction.setYear(selectYear);
        transaction.setMonth(selectMonth);
        transaction.setUserId(userId);
        if(sortName.trim().length() > 0){  //사용자가 입력한 분류명이 길이가 공백을 제외하고 0보다 클경우에만 넣어준다.
            transaction.setSortName(sortName); //사용자가 검색하고 싶은 분류명을 담는다.
//        System.out.println(transaction.getSortName());
        }else {
            transaction.setSortName("");
        }

        //targetBudget -> null or 예산액
        Integer targetBudget = transactionService.getTargetBudget(transaction);
        model.addAttribute("targetBudget", targetBudget);

        //transactionValue -> [{price=8000, type=수입}, {price=8000, type=지출}, {price=8000, type=수입}, {price=8000, type=지출},......
        //incomeSum -> 총 수입액, expendSum -> 총 지출액
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
        model.addAttribute("incomeSum", incomeSum);
        model.addAttribute("expendSum", expendSum);

        //leftMoney -> -1 or 예산액 - 총 지출액
        Integer leftMoney = -1;
        if (targetBudget != null) { //사용자가 예산값을 지정해 두어 null값이 아니라면
            leftMoney = targetBudget - expendSum; //월 목표 예산에서 - 월 총 소비
        }
        model.addAttribute("leftMoney", leftMoney);

        //transactionHistory -> 실질적으로 원한는 전체 데이터를 DB에서 가지고 와서 담는 객체?
        // -> [{transaction_date=2021-05-31, price=8000, name=기타, memo=이자, type=수입}, {transaction_date=2021-05-31, price=8000, .......
        transaction.setSelectYear(String.valueOf(selectYear).substring(2));
        transaction.setSelectMonth(selectMonth);

//        System.out.println(transaction.getUserId());
//        System.out.println(transaction.getSelectYear());
//        System.out.println(transaction.getMonth());

        if(typeRadio.equals("searchMonth")){
            //1. 사용자가 radio button을 월별 검색으로 했을 경우 해당 year, month에 해당하는 데이터만 불러온다.
            List<Map<String, Object>> transactionHistory = transactionService.getTransactionHistoryByMonth(transaction);
//            System.out.println(sortName);
//            System.out.println(transactionHistory);
            model.addAttribute("transactionHistory", transactionHistory);

            //getDailyTotalData -> transactionHistory에서 필요로 하는 데이터를 가공해서 클라이언트로 보내줄 데이터를 담는 List?
            // -> [{transaction_date=2021-05-31, dayCntExpend=80000, dayCntIncome=80000}, {transaction_date=2021-05-29, dayCntExpend=80000, dayCntIncome=80000}, {transaction_date=2021-05-16, dayCntExpend=80000, dayCntIncome=80000}]
            List<Map<String, Object>> distinctTransactionHistory = new ArrayList<Map<String, Object>>();
            distinctTransactionHistory = transactionService.getDistinctTransactionHistory(transactionHistory);

            List<Map<String, Object>> dayCntExpend = transactionService.getDayCntExpend(transaction); //일별 총 지출 내역 합계
            List<Map<String, Object>> dayCntIncome = transactionService.getDayCntIncome(transaction); //일별 총 수입 내역 합계
            List<Map<String,Object>> getDailyTotalData = transactionService.getDailyTotalData(distinctTransactionHistory, dayCntExpend, dayCntIncome);
            model.addAttribute("getDailyTotalData", getDailyTotalData);


            //dateCnt -> 존재하는 날짜 갯수
            int dateCnt = transactionService.getDateCnt(getDailyTotalData);
            model.addAttribute("dateCnt", dateCnt);

            //페이지가 리로딩 되어 다음 요청을 할때 userId가 필요하므로 보내주어야 된다.
            model.addAttribute("userId", userId);
            //월별 검색에서 사용자가 입력한 연도와 월을 넘겨주어애 input type=month에 value값으로 해당 연,월을 보내기 위해 넘겨준다.
            model.addAttribute("selectYear", selectYear);
            model.addAttribute("selectMonth", selectMonth);
            model.addAttribute("sortName", sortName);

        }



        return "thymeleaf/content/otherTransaction";
    }



}
