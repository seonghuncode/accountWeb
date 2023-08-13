package dao;


import controller.TransactionController;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class TransactionRepositoryImpl implements  TransactionRepository {

    @Autowired
    SqlSession sqlSession;

    public int getPrimaryId(String userId){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.getPrimaryId", userId);
    }

    public Integer getTargetBudget(TransactionController.Transaction transaction){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.getTargetBudget", transaction);
    }

    public List<Map<String, Object>> getTransactionValue(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getTransactionValue", transaction);
    }

    public List<Map<String, Object>> getTransactionHistory(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getTransactionHistory", transaction);
    }

    public List<Map<String,Object>> getDayCntExpend(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCentExpend", transaction);
    }

    public List<Map<String,Object>> getDayCntIncome(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCentIncome", transaction);
    }

    public List<Map<String, Object>> getTransactionHistoryByMonth(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getTransactionHistoryByMonth", transaction);
    }

    public List<Map<String,Object>> getDayCntExpendBySearchMonth(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCntExpendBySearchMonth", transaction);
    }

    public List<Map<String,Object>> getDayCntIncomeBySearchMonth(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCntIncomeBySearchMonth", transaction);
    }

    public List<Map<String, Object>> getTransactionHistoryByPeriod(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getTransactionHistoryByPeriod", transaction);
    }

    public List<Map<String,Object>> getDayCntExpendByPeriod(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCntExpendByPeriod", transaction);
    }

    public List<Map<String,Object>> getDayCntIncomeByPeriod(TransactionController.Transaction transaction){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getDayCntIncomeByPeriod", transaction);
    }

    public List<Map<String, Object>> getSortListShow(TransactionController.Sort sort){
        return sqlSession.selectList("dao.TransactionRepositoryImpl.getSortListShow", sort);
    }


    public String getCheckExistSortName(Map<String, Object> sortData){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.getCheckExistSortName", sortData);
    }

    public int doAddSortName(Map<String, Object> sortData){
        return sqlSession.insert("dao.TransactionRepository.doAddSortName", sortData);
    }

    //doModifySortName -> tryModifySortName으로 수정 해야 하는가??
    public int tryModifytSortName(Map<String, Object> sortData){
        return sqlSession.update("dao.TransactionRepository.doModifySortName", sortData);
    }

    public int tryDeleteSortName(Map<String, Object> sortData){
        return sqlSession.delete("dao.TransactionRepository.tryDeleteSortName", sortData);
    }

    //사용자가 특정 날짜에 예산액을 설정 했는지 여부를 확인한다
    public int checkBudgetByUserId(Map<String, Object> data1) {
        return sqlSession.selectOne("dao.TransactionRepository.checkBudgetByUserId", data1);
    }


    public int budgetInsert(Map<String, Object> data1){
        return sqlSession.insert("dao.TransactionRepository.budgetInsert", data1);
    }

    public int budgetUpdate(Map<String, Object> data1){
        return sqlSession.update("dao.TransactionRepository.budgetUpdate", data1);
    }

    //거래내역 추가 요청 로직
    public int doAddTransactionHistory(TransactionController.AddTransactionHistory addTransactionHistory){
        return sqlSession.insert("dao.TransactionRepository.doAddTransactionHistory", addTransactionHistory);
    }

    //분류명을 통해 해당 분류명의 PK값을 요청하는 로직
    public Integer getSortNamePrimaryId(Map<String, Object> sortInfo){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.getSortNamePrimaryId", sortInfo);
    }

    //분류명 삭제시 해당 분류명을 참조하고 있는 거래내역 데이터가 있는지 확인하는 쿼리
    public Integer checkBeforeDeleteSortName(Map<String, Object> sortData){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.checkBeforeDeleteSortName", sortData);
    }

    //분류명 삭제시 미분류라는 이름의 분류명이 있는지 존재 여부를 확인하는 쿼리
    public Integer existNoSortName(Map<String, Object> sortData){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.existNoSortName", sortData);
    }

    //해당 회원이 미분류라는 분류명을 갖고 있지 않을 경우 미분류라는 분류명을 생성하는 쿼리
    public Integer insertNoSortName(Map<String, Object> sortData){
        return sqlSession.insert("dao.TransactionRepository.insertNoSortName", sortData);
    }

    //삭제시 해당 분류명을 참조하는 거래내역의 분류명을 미분류라는 분류명을 참조하도록 하는 쿼리
    public Integer updateSortNameForNoSortName(Map<String, Object> sortData){
        return sqlSession.update("dao.TransactionRepository.updateSortNameForNoSortName", sortData);
    }

    //삭제시 해당 회원이 가지고 있는 미분류 라는 분류명의 날짜를 반환 받는 쿼리
    public String getNoSortNameDate(Map<String, Object> sortData){
        return sqlSession.selectOne("dao.TransactionRepository.getNoSortNameDate", sortData);
    }

    //삭제시 존재하는 미분류 분류명의 날짜가 전체가 아닌 경우 전체로 변경하는 쿼리
    public Integer changeNoSortNameForAllDate(Map<String, Object> sortData){
        return sqlSession.update("dao.TransactionRepository.changeNoSortNameForAllDate", sortData);
    }

    //미분류 이름의 분류명 PK값을 구하는 쿼리
    public Integer noSortNameId(Map<String, Object> sortData){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.noSortNameId", sortData);
    }

    //거래내역 페이지 에서 특정 거래내역의 데이터를 수정하는  쿼리
    public int doModifyTransactionField(Map<String, Object> data){
        return sqlSession.update("dao.TransactionRepository.doModifyTransactionField", data);
    }

    //거래내역 페이지 에서 특정 거래내역의 데이터를 삭제하는  쿼리
    public int deleteTransactionField(Map<String, Object> data){
        return sqlSession.delete("dao.TransactionRepository.deleteTransactionField", data);
    }

    //네비게이션바에서 사용자가 회원정보 버튼을 클릭했을 경우 현재 로그인한 회원의 PK를 통해 해당 회원의 데이터를 가지고 오는 쿼리
    public Map<String, Object> getUserInfo(int primaryId){
        return sqlSession.selectOne("dao.TransactionRepository.getUserInfo", primaryId);
    }

    //네비게이션바 에서 통계 버튼을 클릭하면 현재 로그인 되어 있는 당월의 지출 내역을 분류명 별로 합계를 내어 가지고 오는 쿼리
    public List<Map<String, Object>> getTransactionSumBySortName(Map<String, Object> data){
        return sqlSession.selectList("dao.TransactionRepository.getTransactionSumBySortName", data);
    }

    //현재 로그인 되어 있는 회원의 특정월에 사용한 type에 대한 총 합한 금액을 구하는 로직
    public Integer getTotalPrice(Map<String, Object> data){
        return sqlSession.selectOne("dao.TransactionRepository.getTotalPrice", data);
    }



    //통계 페이지 에서 사용자가 기간 검색 조건에서 입력한 기간에 해당하는 지출 내역을 분류명 별로 합계를 내어 가지고 오는 쿼리
    public List<Map<String, Object>> getTransactionSumBySortNameAndPeriod(Map<String, Object> data){
        return sqlSession.selectList("dao.TransactionRepository.getTransactionSumBySortNameAndPeriod", data);
    }

    //현재 로그인 되어 있는 회원의 특정 기간 동안의 목표 예산의 총합을 가지고 오는 로직
    public Integer getTargetBudgeByPeriod(Map<String, Object> data){
        return sqlSession.selectOne("dao.TransactionRepository.getTargetBudgeByPeriod", data);
    }

    //현재 로그인한 회원의 특정 날짜 기간에 대해 총 사용한 지출 or 수입 합계를 구하는 로직
    public Integer getTotalPriceByPeriod(Map<String, Object> data){
        return sqlSession.selectOne("dao.TransactionRepository.getTotalPriceByPeriod", data);
    }



}
