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

}
