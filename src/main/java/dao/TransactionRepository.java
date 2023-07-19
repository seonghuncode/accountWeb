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

    List<Map<String,Object>> getDayCntExpendBySearchMonth(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDayCntIncomeBySearchMonth(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionHistoryByPeriod(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDayCntExpendByPeriod(TransactionController.Transaction transaction);

    List<Map<String,Object>> getDayCntIncomeByPeriod(TransactionController.Transaction transaction);

    List<Map<String, Object>> getSortListShow(TransactionController.Sort sort);

    String getCheckExistSortName(Map<String, Object> sortData);

    int doAddSortName(Map<String, Object> sortData);

    int tryModifytSortName(Map<String, Object> sortData);

    int tryDeleteSortName(Map<String, Object> sortData);

    int checkBudgetByUserId(Map<String, Object> data1);

    int budgetInsert(Map<String, Object> data1);

    int budgetUpdate(Map<String, Object> data1);

}
