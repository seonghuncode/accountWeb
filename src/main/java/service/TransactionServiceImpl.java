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
    private int originDateCnt;

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
            if (map1.containsKey("dayCntExpend") == false) {
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
            if (map1.containsKey("dayCntIncome") == false) {
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
    public int getDateCnt(List<Map<String, Object>> getDailyTotalData) {


        int dateCnt = 0;
        for (int i = 0; i < getDailyTotalData.size(); i++) {
            dateCnt++;
        }

        originDateCnt = dateCnt; //전역변수로 getDateCnt2메소드 에서도 사용이 가능하도록 지정

        //System.out.println(dateCnt);
        if (dateCnt < 3) {  //한페이지 에 3일치 데이터를 보여 줄 예정인데 데이터가 3개보다 작다면 전체 0,1,2의 갖고 있는 데이터를 전부 보여주기 위함
            return dateCnt;
        } else {  //데이터가 3개 이상이라면 3을 return해 주어 3개의 데이터만 보이도록 한다.
            return 3;
        }

    }

    //특정 회원 지출 내역 페이지에서 추후 사용자가 7일치 내역 더보기 버튼을 클릭할 경우 한페이지에 보여줄 테이블 갯수릐 최대치를 변경해서 return해주는 로직
    public Map<String, Object> getDateCnt2(int dateCnt) {

        int changeDateCnt = dateCnt + 7;
        Map<String, Object> map = new HashMap<String, Object>();

        if (originDateCnt > changeDateCnt) { //기존의 보여지는 페이지 일수의 합이 실제 해당 회원이 가지고 있는 일수 데이터 보다 작을 경우 +7한 값 return
            map.put("changeDateCnt", changeDateCnt);
        } else if (originDateCnt <= changeDateCnt) { //+7한 값이 실제 해당 회원이 갖고 있는 데이터를 넘어갈 경우 전체 회원의 갯수를return
            map.put("changeDateCnt", originDateCnt);
            map.put("lastPage", 1); //더이상 볼 데이터가 없다는 것을 알려주기 위해
        }
//        System.out.println(originDateCnt);

        return map;
    }

    public String getThisYear() {

        LocalDate now = LocalDate.now();
        int year = now.getYear(); //ex. 2023
        String changeYear = String.valueOf(year).substring(2); //ex. 23

        return changeYear;
    }

    public String getThisMonth() {

        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();  //ex. 3
        String changeMonth = String.format("%02d", month); //ex. 03

        return changeMonth;
    }

    public List<Map<String, Object>> getTransactionHistoryByMonth(TransactionController.Transaction transaction) {
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

    public List<Map<String, Object>> getTransactionHistoryByPeriod(TransactionController.Transaction transaction) {
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

    public List<Map<String, Object>> getSortListShow(TransactionController.Sort sort) {
        return transactionRepository.getSortListShow(sort);
    }

    //지출 내역 에서 분류명 관리에서 분류명명 추가를 완료 했을때 입력한 분류명이 DB에 존재하는지 확인하는 로직
    public String getSortAddProcess(Map<String, Object> sortData) {

        //클라이언트로 부터 Ajax로 받은 값
//        System.out.println(sortData.get("addSort")); // 추가할 분류명
//        System.out.println(sortData.get("sortDate")); //적용할 월
//        System.out.println(sortData.get("result")); //결과에 대한 값값

        //추가하려고 하는 분류명이 존재 하는지 확인하는 로직
        String checkExistSortName = transactionRepository.getCheckExistSortName(sortData);
//        System.out.println("result >>> " + checkExistSortName );

        return checkExistSortName;
    }

    //사용자가 분류명 관리에서 완료버튼을 클릭했을때 입력한 분류명이 DB에 존재 하지 않을 경우 해당 분류명을 DB에 추가하는 로직
    public Map<String, Object> tryAddSortName(Map<String, Object> sortData, String validSortName) {

//         System.out.println("===================ServiceImpl");
//         System.out.println(sortData);
//         System.out.println(validSortName);
//         System.out.println("=============================");
        if (validSortName.equals("0")) {  //사용자가 입력한 분류명이 DB에 존재X -> 추가 가능
            int doAddSortName = transactionRepository.doAddSortName(sortData);  //1이면 성공, 0이면 실패
            //System.out.println("쿼리 결과 : " + doAddSortName);
            sortData.put("result", "true");
            if (doAddSortName != 1) {
                System.out.println("분류명 추가 쿼리 오류 발생");
            }
        } else {
            sortData.put("result", "false");
        }

        return sortData;
    }


    public Map<String, Object> tryModifytSortName(Map<String, Object> sortData, String validSortName) {

        if (validSortName.equals("0")) {  //사용자가 입력한 분류명이 DB에 존재X -> 추가 가능
            int doModifySortName = transactionRepository.tryModifytSortName(sortData);
            //System.out.println("쿼리 결과 : " + doAddSortName);
            sortData.put("result", "true");
            if (doModifySortName != 1) {
                System.out.println("분류명 수정 쿼리 오류 발생");
            }
        } else {
            sortData.put("result", "false");
        }


        return sortData;
    }

    //수정할 분류명이 이미 존재하는 분류명인지 체크해주는 로직
    public String getSortModifyProcess(Map<String, Object> sortData) {

        //기존 분류명 추가 에서 추가할 분류명에 대해 중복 체크를 하는 로직을 사용하기 위해 키값을 수정 해야 한다.
        modifyKey(sortData, "modifySort", "addSort");

        //수정 하려고 하는 분류명이 존재 하는지 확인하는 로직
        String checkExistSortName = transactionRepository.getCheckExistSortName(sortData);
//        System.out.println("result >>> " + checkExistSortName );

        //사용후 키값을 원래대로 되돌린다.
        modifyKey(sortData, "addSort", "modifySort");

        return checkExistSortName;
    }

    //자바스크립트 에서 받은 키 이름을 변경해주는 로직(분류명 추가 에서 만든 로직을 분류명 수정 하기 에서 사용하기 위해서 잠시 key이름을 바꾸어 사용하기 위한 함수)
    public static <K, V> void modifyKey(Map<K, V> map, K oldKey, K newKey) {
        V value = map.remove(oldKey); // 기존 키-값 쌍 제거
        if (value != null) {
            map.put(newKey, value); // 새로운 키-값 쌍 추가
        }
    }


    //
    public Map<String, Object> tryDeleteSortName(Map<String, Object> sortData) {

        int doModifySortName = transactionRepository.tryDeleteSortName(sortData);
        //System.out.println("쿼리 결과 : " + doAddSortName);
        sortData.put("result", "true");
        if (doModifySortName != 1) { //삭제를 진행하는 쿼리 오류 발생
            System.out.println("분류명 삭제 쿼리 오류 발생");
        }

        return sortData;
    }


    public Map<String, Object> setBudgetAmount(Map<String, Object> data1) {

        String loginId = (String) data1.get("loginId");

        //현재 로그인 되어있는 아이디의 PK값을 구한다
        int primaryId = getPrimaryId(loginId);

        data1.put("loginIdPK", primaryId);  //data1 json형태에 회원 아이디의 PK값을 추가 해준다.

        Map<String, Object> result = new HashMap<String, Object>();

        //사용자가 이전에 예산액을 작성한적이 있는지 확인(없어서 null값이 return되면 0으로, 값이 있다면 1(중복이 될수 없기 때문에 1초과로 나올 수 없다.))
        Integer existCnt = transactionRepository.checkBudgetByUserId(data1); //사용자가 해당 월에 예산액을 설정한 적이 있는지 확인
        existCnt = existCnt != null ? existCnt : 0;
        //System.out.println(existCnt);

        data1.put("existCnt", existCnt); //0이면 insert쿼리 실행, 1이면 update 쿼리를 실행 하기 위해서 data1에 넣는다.
        //System.out.println(data1);

        if (existCnt == 0) {
            int budgetInsert = transactionRepository.budgetInsert(data1);
            if (budgetInsert == 1) { //쿼리가 성공하면
                result.put("result", "success");
            } else {
                result.put("result", "fail");
            }
        } else if (existCnt == 1) {
            int budgetUpdate = transactionRepository.budgetUpdate(data1);
            if (budgetUpdate == 1) { //쿼리가 성공하면
                result.put("result", "success");
            } else {
                result.put("result", "fail");
            }
        }
        return result;
    }



    //거래내역을 추가하는 쿼리를 데이터베이스에 요청하고 결과로 성공시 1 실패시 0 을 반환받은 값을 통해 result에 성공여부를 담아 return하는 로직
    public Map<String, Object> doAddTransactionHistory(TransactionController.AddTransactionHistory addTransactionHistory) {

        int process = transactionRepository.doAddTransactionHistory(addTransactionHistory);
        Map<String, Object> result = new HashMap<String, Object>();

        //System.out.println("쿼리 성공 여부 : " + process);
        if (process == 1) { //삭제를 진행하는 쿼리 오류 발생
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;
    }


    //파라미터로 받은 분류명의 PK를 구하는 로직
    public Integer getSortNamePrimaryId(Map<String, Object> sortInfo) {
        return transactionRepository.getSortNamePrimaryId(sortInfo);
    }

    //분류명 삭제에 사용되는 필요 로직--------------------------------------------------
    public Integer checkBeforeDeleteSortName(Map<String, Object> sortData) {
        return transactionRepository.checkBeforeDeleteSortName(sortData);
    }

    public Integer existNoSortName(Map<String, Object> sortData) {
        return transactionRepository.existNoSortName(sortData);
    }

    public Integer insertNoSortName(Map<String, Object> sortData) {
        return transactionRepository.insertNoSortName(sortData);
    }

    public Integer updateSortNameForNoSortName(Map<String, Object> sortData) {
        return transactionRepository.updateSortNameForNoSortName(sortData);
    }

    public String getNoSortNameDate(Map<String, Object> sortData) {
        return transactionRepository.getNoSortNameDate(sortData);
    }

    public Integer changeNoSortNameForAllDate(Map<String, Object> sortData) {
        return transactionRepository.changeNoSortNameForAllDate(sortData);
    }

    public Integer noSortNameId(Map<String, Object> sortData) {
        return transactionRepository.noSortNameId(sortData);
    }

    //분류명 삭제에 사용되는 필요 로직--------------------------------------------------

    //분류명 삭제시 해당 분류명을 참조하는 거래내역이 있는지 확인하고 문제없이 해당 분류명을 삭제 하는 로직
    public Map<String, Object> sortNameDeleteProcess(Map<String, Object> sortData) {

//        System.out.println("Controller " + sortData);
        String selSortValue = (String)sortData.get("selSort");
        String resultValue = (String)sortData.get("result");

        //step1. 삭제할 분류명을 참조하고 있는 거래내역 테이블이 있는지 확인(있다면 참조하는 분류명의 PK값을 반환 받는다 == 해당 값은 삭제할 PK값이다.)
        Integer check1 = checkBeforeDeleteSortName(sortData);
//        System.out.println("분류명 참조 여부 : " + check1);
        //step2. 참조하는 거래내역이 없다면 정상적으로 삭제 진행
        if (check1 == null) {
            Map<String, Object> result = tryDeleteSortName(sortData); //기존 분류명 삭제
//            System.out.println("===result===");
//            System.out.println(result);
            return sortData;
        }
        //step3. 참조하는 거래내역이 있다면 거래내역의 분류명을 미분류로 지정
        if(check1 != null){
            // 1. 미분류라는 분류명이 없다면 만들고(미분류 라는 분류명 존재 여부 체크) 및 참조하는 거래내역의 분류명을 미분류로 수정
            Integer check2 = existNoSortName(sortData);
//            System.out.println("미분류 분류명 존재 여부(check2) : " + check2);

            sortData.put("result", check1);
//            System.out.println("수정한 sortData : " + sortData);

            if(check2 == null){
                Integer InsertResult = insertNoSortName(sortData); //미분류 라는 이름의 분류명 생성
//                System.out.println("미분류 분류명 추가(InsertResult) : " + InsertResult);
                Integer noSortNameId =  noSortNameId(sortData);//미분류라는 분류명의 id값을 가지고온다.
                sortData.put("selSort", noSortNameId);
                //기존에 거래내역에서 참조하는 분류명을 미분류로 수정 하는 작업
                Integer UpdateSortName = updateSortNameForNoSortName(sortData);
//                System.out.println("참조하던 분류명을 미분류를 참조하도록 수정 쿼리(UpdateSortName) : " + UpdateSortName);

                sortData.put("selSort", selSortValue); //삭제전 selSort를 다시 원래 선택 값으로 변경
                Map<String, Object> result = tryDeleteSortName(sortData); //기존 분류명 삭제

                sortData.put("result", "success");
            }else if(check2 != null){ //미분류 분류명이 이미 존재할 경우
                // 2. 만약 존재하는 미분류라는 분류명이 특정월에서 사용중 이라면 기간을 전체로 수정
                String noSortNameDate = getNoSortNameDate(sortData);//존재하는 미분류라는 분류명에 대해 날짜를 확인
                if(!noSortNameDate.equals("1111-12-12")){ //미분류라는 분류명이 전체가 아니라면 전체 사용으로 변경하는 쿼리
                    Integer changeResult = changeNoSortNameForAllDate(sortData);//기존에 존재하는 미분류 분류명을 사용 범위를 전체로 수정하는 쿼리
                    System.out.println("changeResult" + changeResult);
                }
                Integer noSortNameId =  noSortNameId(sortData);//미분류라는 분류명의 id값을 가지고온다.
                sortData.put("selSort", noSortNameId);
                // 3. 미분류라는 분류명이 있다면 해당 분류명을 사용해 적용)
                Integer UpdateSortName = updateSortNameForNoSortName(sortData);
//                System.out.println("참조하던 분류명을 미분류를 참조하도록 수정 쿼리(UpdateSortName) : " + UpdateSortName);


                sortData.put("selSort", selSortValue); //삭제전 selSort를 다시 원래 선택 값으로 변경
                Map<String, Object> result = tryDeleteSortName(sortData); //기존 분류명 삭제

                sortData.put("result", "success");
                sortData.put("selSort", selSortValue);
            }
        }

//        Map<String, Object> result = transactionService.tryDeleteSortName(sortData);
////        System.out.println("===result===");
////        System.out.println(result);

        return sortData;
    }



    public Map<String, Object> doModifyTransactionField(Map<String, Object> data) {

        //날짜를 쿼리 에서 사용하기 좋도록 연도와 일로 나눈다
        String date = (String)data.get("selDate");
        String year = date.substring(2, 4);
        String month = date.substring(5, 7);
        data.put("year", year);
        data.put("month", month);
//        System.out.println("date(data에서 꺼낸 날짜) : " + date);

        //사용자의 아이디를 통해 해당 아이디의 PK값을 구한다 -> 해당 PK값을 data객체에 넣어준다
        String userId = (String)data.get("userId");
        int userIdPK = transactionRepository.getPrimaryId(userId);
        data.put("userIdPK", userIdPK);

        //분류명의 Pk값을 알기 위해서는 어떤회원의 분류명의 pk인지 구하기 때문에 특정 회원PK, 분류명 이름이 필요하다.
        String selSortValue = (String)data.get("selSortName"); //분류명을 data에서 추출
        Map<String, Object> sortInfo = new HashMap<String, Object>();
        sortInfo.put("userIdPK", userIdPK);
        sortInfo.put("sortName", selSortValue);

        int sortNamePK = transactionRepository.getSortNamePrimaryId(sortInfo);//바꾸고 싶은 분류명의 PK값을 구하는 쿼리(거래내역 테이블의 sortId에 넣을 값)
        data.put("sortNamePK", sortNamePK);

//        System.out.println("======data 최종 데이터======");
//        System.out.println(data);

        int result2 = transactionRepository.doModifyTransactionField(data); //거래내역 수정 쿼리
//        System.out.println("result2 : " + result2);


        Map<String, Object> result = new HashMap<String, Object>();

       if(result2 == 1){
           result.put("result", "success");
       }else{
           result.put("result", "fail");
       }

        return result;
    }




    public Map<String, Object> deleteTransactionField(Map<String, Object> data) {

        //날짜를 쿼리 에서 사용하기 좋도록 연도와 일로 나눈다
        String date = (String)data.get("transactionDate");
        String year = date.substring(2, 4);
        String month = date.substring(5, 7);
        data.put("year", year);
        data.put("month", month);

        //사용자의 아이디를 통해 해당 아이디의 PK값을 구한다 -> 해당 PK값을 data객체에 넣어준다
        String userId = (String)data.get("nowLoginUserId");
        int userIdPK = transactionRepository.getPrimaryId(userId);
        data.put("userIdPK", userIdPK);

        //분류명의 Pk값을 알기 위해서는 어떤회원의 분류명의 pk인지 구하기 때문에 특정 회원PK, 분류명 이름이 필요하다.
        String selSortValue = (String)data.get("transactionName"); //분류명을 data에서 추출
        Map<String, Object> sortInfo = new HashMap<String, Object>();
        sortInfo.put("userIdPK", userIdPK);
        sortInfo.put("sortName", selSortValue);


//        System.out.println("======최종 data======");
//        System.out.println(data);

        int result2 = transactionRepository.deleteTransactionField(data);

        Map<String, Object> result = new HashMap<String, Object>();

        if(result2 == 1){
            result.put("result", "success");
        }else{
            result.put("result", "fail");
        }

        return result;
    }


    public Map<String, Object> getUserInfo(int primaryId) {
        return transactionRepository.getUserInfo(primaryId);
    }



    public List<Map<String, Object>> getTransactionSumBySortName(String userId, String year, String month, int primaryId, String type) {

        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("primaryId", primaryId);
        data.put("userId", userId);
        data.put("year", year);
        data.put("month", month);
        data.put("type", type);
//        System.out.println("=============");
//        System.out.println(data.get("primaryId"));
//        System.out.println(data.get("userId"));
//        System.out.println(data.get("year"));
//        System.out.println(data.get("month"));
//        System.out.println(data.get("type"));
//        System.out.println("=============");

        //현재 로그인 되어있는 특정 날짜의 특정 type에 대해 분류명 별로 금액을 합한 데이터를 가지고 오는 쿼리
//        System.out.println(transactionRepository.getTransactionSumBySortName(data));

        return transactionRepository.getTransactionSumBySortName(data);
    }




    //현재 로그인 되어 있는 회원의 특정월에 사용한 type에 대한 총 합한 금액을 구하는 로직
    public Integer getTotalPrice(int primaryId, String year, String month, String type) {

        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("primaryId", primaryId);
        data.put("year", year);
        data.put("month", month);
        data.put("type", type);
        
        Integer result = transactionRepository.getTotalPrice(data);
        
        return result;
    }



    public List<Map<String, Object>> getTransactionSumBySortNameAndPeriod(String userId, String startYear, String startMonth, String endYear, String endMonth, int primaryId, String type, String startDate, String endDate) {

        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("primaryId", primaryId);
        data.put("userId", userId);
        data.put("startYear", startYear);
        data.put("startMonth", startMonth);
        data.put("endYear", endYear);
        data.put("endMonth", endMonth);
        data.put("type", type);
        data.put("startDate", startDate);
        data.put("endDate", endDate);
//        System.out.println("=============");
//        System.out.println(data.get("primaryId"));
//        System.out.println(data.get("userId"));
//        System.out.println(data.get("startYear"));
//        System.out.println(data.get("startMonth"));
//        System.out.println(data.get("endYear"));
//        System.out.println(data.get("endMonth"));
//        System.out.println(data.get("type"));
//        System.out.println("=============");

        //현재 로그인 되어있는 특정 날짜의 특정 type에 대해 분류명 별로 금액을 합한 데이터를 가지고 오는 쿼리
//        System.out.println(transactionRepository.getTransactionSumBySortName(data));

        return transactionRepository.getTransactionSumBySortNameAndPeriod(data);
    }


    //현재 로그인 되어 있는 회원의 특정 기간 동안의 목표 예산의 총합을 가지고 오는 로직
    public Integer getTargetBudgeByPeriod(int primaryId, String startDate, String endDate) {

        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("primaryId", primaryId);
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        Integer result = transactionRepository.getTargetBudgeByPeriod(data);

        return result;
    }


    //현재 로그인한 회원의 특정 날짜 기간에 대해 총 사용한 지출 or 수입 합계를 구하는 로직
    public Integer getTotalPriceByPeriod(int primaryId, String startDate, String endDate, String type) {

        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("primaryId", primaryId);
        data.put("startDate", startDate);
        data.put("endDate", endDate);
        data.put("type", type);

        Integer result = transactionRepository.getTotalPriceByPeriod(data);

        return result;
    }


    //기간별 거래내역 검색의 경우 해당 하는 기간의 목표예산을 전부 합해주는 로직
    public Integer periodTotalBudget(TransactionController.Transaction transaction) {
        return transactionRepository.periodTotalBudget(transaction);
    }

    
    //기간별 거래내역 검색의 경우 해당 기간에 해당 하는 값들을 가지고 오는 로직
    public List<Map<String, Object>> getTransactionValueByPeriod(TransactionController.Transaction transaction) {
        return transactionRepository.getTransactionValueByPeriod(transaction);
    }


    //거래내역 부분에서 사용자 자가 월별 검색 또는 기간별 검색을 할경우 검색월 기준 현황에 보여줄 남은 목표 예산, 총 수입, 총 지출에 대한 데이터를 구해주는 로직
    //(/showTransaction/whichSelect 오는 요청)
    public Map<String, Object> calculateForSearchInfo(List<Map<String, Object>> transactionValue, Integer targetBudget){

        int incomeSum = 0; //수입 총액
        int expendSum = 0; //지출 총액
        for (Map<String, Object> item : transactionValue) {
            if (item.get("type").equals("지출")) {
                expendSum += (int) item.get("price");
            } else if (item.get("type").equals("수입")) {
                incomeSum += (int) item.get("price");
            }
        }

        //leftMoney -> -1 or 예산액 - 총 지출액
        Integer leftMoney = -1;
        if (targetBudget != null) { //사용자가 예산값을 지정해 두어 null값이 아니라면
            leftMoney = targetBudget - expendSum; //월 목표 예산에서 - 월 총 소비
        }


        //쿼리에 필요한 데이터들를 Map형태로 넣는다.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("incomeSum", incomeSum);
        data.put("expendSum", expendSum);
        data.put("leftMoney", leftMoney);

        return data;

    }








}
