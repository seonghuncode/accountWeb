package service;

import controller.TransactionController;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TransactionService {

    int getPrimaryId(String userId);

    Integer getTargetBudget(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionValue(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionHistory(TransactionController.Transaction transaction);

    List<Map<String, Object>> getDistinctTransactionHistory(List<Map<String, Object>> transactionHistory);

}
