package dao;

import controller.TransactionController;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository {

    int getPrimaryId(String userId);

    Integer getTargetBudget(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionValue(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionHistory(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDayCntExpend(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDayCntIncome(TransactionController.Transaction transaction);

    List<Map<String,Object>> getTransactionHistoryByMonth(TransactionController.Transaction transaction);
}
