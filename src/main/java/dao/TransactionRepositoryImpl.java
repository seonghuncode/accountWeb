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

}
