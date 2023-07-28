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
    public int getSortNamePrimaryId(String sortName){
        return sqlSession.selectOne("dao.TransactionRepositoryImpl.getSortNamePrimaryId", sortName);
    }

}
