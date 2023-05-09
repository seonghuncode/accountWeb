package service;

import controller.TransactionController;
import dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    //특정 회원 지출 내역에서 해당 회원이 실제 DB에 가지고 있는 전체 지출 일수의 갯수를 저장하고 있다.(getDateCnt2에서 사용)
    private  int originDateCnt;

    public int getPrimaryId(String userId) {
        return transactionRepository.getPrimaryId(userId);
    }

    public Integer getTargetBudget(TransactionController.Transaction transaction) {
        return transactionRepository.getTargetBudget(transaction);
    }

    public List<Map<String, Object>> getTransactionValue(TransactionController.Transaction transaction) {
        return transactionRepository.getTransactionValue(transaction);
    }

    public List<Map<String, Object>> getTransactionHistory(TransactionController.Transaction transaction) {
        return transactionRepository.getTransactionHistory(transaction);
    }

    public List<Map<String, Object>> getDistinctTransactionHistory(List<Map<String, Object>> transactionHistory) {

        List<Map<String, Object>> distinctTransactionHistory = new ArrayList<Map<String, Object>>();
        Object date = "0000-00-00"; //날짜가 내림차순으로 정렬되어 반복문을 돌리면서 기존에 나왔던 날짜를 저장 하므로써 중복된 날짜가 나오면 패스

        for (Map<String, Object> map : transactionHistory) {  //내림차순으로 정렬된 지출 내역이 끝날때 까지 반복문
//            System.out.println(map);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
//                System.out.println("==========================");
//                System.out.println("key: " + key + " | value: " + value);
//                System.out.println("==========================");
//                System.out.println(value);


                Map<String, Object> map1 = new HashMap<String, Object>();
                //중복되는 날짜를 제거하여 저장하는 로직
                if (key.equals("transaction_date") && !(value.equals(date))) { //key값이 transaction_date이고 이미 나온 날짜가 아닐 경우
                    map1.put("transaction_date", value); //map1에 해당 날짜를 넣어 준다
//                    System.out.println("value : " + value);
                    date = value;
                    //날짜를 distinctTransactionHistory에 담는다.
                    distinctTransactionHistory.add(map1);
                }
            }
        }
//        System.out.println(distinctTransactionHistory);

        return distinctTransactionHistory;
    }


    //일별 총 지출 합계를 가지고 오는 로직
    public List<Map<String, Object>> getDayCntExpend(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntExpend(transaction);
    }

    //일별 총 수입 합계를 가지고 오는 로직
    public List<Map<String, Object>> getDayCntIncome(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntIncome(transaction);
    }

    //기존 특정 메인 페이지 에서 테이블을 일별로 보여주기 위한 데이터가 날짜, 일별 지출 총 합, 일별 수입 총 합으로 나누어져  따로 불러와서 사용하는데 어려움이 있어
    //==>3개의 데이터를 하나의 map List로 합치는 작업을 해주었다.
    public List<Map<String, Object>> getDailyTotalData(List<Map<String, Object>> distinctTransactionHistory, List<Map<String, Object>> dayCntExpend, List<Map<String, Object>> dayCntIncome) {

        List<Map<String, Object>> getDailyTotalData = new ArrayList<>();
//        System.out.println("distinctTransactionHistory" + distinctTransactionHistory);
        //필요한 데이터 날짜, 지출 합계, 수입 합계
        for (int i = 0; i < distinctTransactionHistory.size(); i++) {

            Map<String, Object> map1 = new HashMap<String, Object>();
            Object date = "";
            for (Map.Entry<String, Object> entry : distinctTransactionHistory.get(i).entrySet()) {
                map1.put("transaction_date", entry.getValue());
                date = entry.getValue();
            }
//            System.out.println("date : " + date);
            Object ExpendDate = "";
            Object total = "0";

            for (int j = 0; j < dayCntExpend.size(); j++) {
                for (Map.Entry<String, Object> entry : dayCntExpend.get(j).entrySet()) {
                    if (entry.getKey().equals("transaction_date")) {
                        ExpendDate = entry.getValue();
//                        System.out.println("ExpendDate : " + ExpendDate);
                    } else if (entry.getKey().equals("total")) {
                        total = entry.getValue();
                    }

                    if (!(total.equals("0")) && ExpendDate.equals(date)) {
                        map1.put("dayCntExpend", total);
//                        System.out.println("ExpendDate2 : " + ExpendDate);
                    }
                }
            }
            //만약 해당 날짜에 대한 지출액 합이 없을 경우 지출액 합에 0값을 넣어 주는 코드
            if(map1.containsKey("dayCntExpend") == false){
                map1.put("dayCntExpend", 0);
            }


            for (int j = 0; j < dayCntIncome.size(); j++) {
                for (Map.Entry<String, Object> entry : dayCntIncome.get(j).entrySet()) {
                    if (entry.getKey().equals("transaction_date")) {
                        ExpendDate = entry.getValue();
//                        System.out.println("dayCntIncome : " + dayCntIncome);
                    } else if (entry.getKey().equals("total")) {
                        total = entry.getValue();
                    }
                    if (!(total.equals("0")) && ExpendDate.equals(date)) {
                        map1.put("dayCntIncome", total);
//                        System.out.println("dayCntIncome : " + dayCntIncome);
                    }
                }
            }
            //만약 해당 날짜에 대한 수입액 합이 없을 경우 수입액 합에 0값을 넣어 주는 코드
            if(map1.containsKey("dayCntIncome") == false){
                map1.put("dayCntIncome", 0);
            }
//            for (Map.Entry<String, Object> entry : dayCntIncome.get(i).entrySet()) {
//                if (entry.getKey().equals("total")) {
//                    map1.put("dayCntIncome", entry.getValue());
//                }
//            }

//            System.out.println(map1);

            getDailyTotalData.add(map1);
        }

        return getDailyTotalData;
    }


    //처음 특정 회원 지출 내역에서 한 페이지에 최대 일수 테이블을 3개만 보여주기 위해 존재하는 로직
    public int getDateCnt(List<Map<String, Object>> getDailyTotalData){


        int dateCnt = 0;
        for(int i = 0; i < getDailyTotalData.size(); i++){
            dateCnt++;
        }
        
        originDateCnt = dateCnt; //전역변수로 getDateCnt2메소드 에서도 사용이 가능하도록 지정
        
        //System.out.println(dateCnt);
        if(dateCnt < 3){  //한페이지 에 3일치 데이터를 보여 줄 예정인데 데이터가 3개보다 작다면 전체 0,1,2의 갖고 있는 데이터를 전부 보여주기 위함
            return dateCnt;
        }else{  //데이터가 3개 이상이라면 3을 return해 주어 3개의 데이터만 보이도록 한다.
            return 3;
        }

    }

    //특정 회원 지출 내역 페이지에서 추후 사용자가 7일치 내역 더보기 버튼을 클릭할 경우 한페이지에 보여줄 테이블 갯수릐 최대치를 변경해서 return해주는 로직
    public Map<String, Object> getDateCnt2(int dateCnt){

        int changeDateCnt = dateCnt+7;
        Map<String, Object> map = new HashMap<String, Object>();

        if(originDateCnt > changeDateCnt){ //기존의 보여지는 페이지 일수의 합이 실제 해당 회원이 가지고 있는 일수 데이터 보다 작을 경우 +7한 값 return
            map.put("changeDateCnt", changeDateCnt);
        }else if(originDateCnt <= changeDateCnt){ //+7한 값이 실제 해당 회원이 갖고 있는 데이터를 넘어갈 경우 전체 회원의 갯수를return
            map.put("changeDateCnt", originDateCnt);
            map.put("lastPage", 1); //더이상 볼 데이터가 없다는 것을 알려주기 위해
        }
//        System.out.println(originDateCnt);

        return map;
    }

    public String getThisYear(){

        LocalDate now = LocalDate.now();
        int year = now.getYear(); //ex. 2023
        String changeYear = String.valueOf(year).substring(2); //ex. 23

        return changeYear;
    }

    public String getThisMonth(){

        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();  //ex. 3
        String changeMonth = String.format("%02d", month); //ex. 03

        return changeMonth;
    }

    public List<Map<String, Object>> getTransactionHistoryByMonth(TransactionController.Transaction transaction){
        return transactionRepository.getTransactionHistoryByMonth(transaction);
    }

    //일별 총 지출 합계를 가지고 오는 로직 (특정월 검색)
    public List<Map<String, Object>> getDayCntExpendBySearchMonth(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntExpendBySearchMonth(transaction);
    }

    //일별 총 수입 합계를 가지고 오는 로직 (특정월 검색)
    public List<Map<String, Object>> getDayCntIncomeBySearchMonth(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntIncomeBySearchMonth(transaction);
    }

    public  List<Map<String, Object>> getTransactionHistoryByPeriod(TransactionController.Transaction transaction){
        return transactionRepository.getTransactionHistoryByPeriod(transaction);
    }


    //일별 총 지출 합계를 가지고 오는 로직 (기간별 검색)
    public List<Map<String, Object>> getDayCntExpendByPeriod(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntExpendByPeriod(transaction);
    }

    //일별 총 수입 합계를 가지고 오는 로직 (기간별 검색)
    public List<Map<String, Object>> getDayCntIncomeByPeriod(TransactionController.Transaction transaction) {
        return transactionRepository.getDayCntIncomeByPeriod(transaction);
    }

}
