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

    List<Map<String, Object>> getDayCntExpend(TransactionController.Transaction transaction);

    List<Map<String, Object>> getDayCntIncome(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDailyTotalData(List<Map<String, Object>>  distinctTransactionHistory, List<Map<String, Object>>  dayCntExpend,List<Map<String, Object>>   dayCntIncome);

    int getDateCnt(List<Map<String, Object>> getDailyTotalData);

    Map<String, Object> getDateCnt2(int dateCnt);

    String getThisYear();

    String getThisMonth();

    List<Map<String, Object>> getTransactionHistoryByMonth(TransactionController.Transaction transaction);


}
