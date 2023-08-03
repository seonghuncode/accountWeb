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

    List<Map<String, Object>> getDayCntExpendBySearchMonth(TransactionController.Transaction transaction);

    List<Map<String, Object>> getDayCntIncomeBySearchMonth(TransactionController.Transaction transaction);

    List<Map<String, Object>> getTransactionHistoryByPeriod(TransactionController.Transaction transaction);

    List<Map<String, Object>> getDayCntExpendByPeriod(TransactionController.Transaction transaction);

    List<Map<String, Object>> getDayCntIncomeByPeriod(TransactionController.Transaction transaction);

    List<Map<String, Object>> getSortListShow(TransactionController.Sort sort);

    String getSortAddProcess(Map<String, Object> sortData);

    Map<String, Object> tryAddSortName(Map<String, Object> sortData,String validSortName);

    Map<String, Object> tryModifytSortName(Map<String, Object> sortData, String validSortName);

    String getSortModifyProcess(Map<String, Object> sortData);

    Map<String, Object> tryDeleteSortName(Map<String, Object> sortData);

    Map<String, Object> setBudgetAmount(Map<String, Object> data1);

    Map<String, Object> doAddTransactionHistory(TransactionController.AddTransactionHistory addTransactionHistory);

    int getSortNamePrimaryId(Map<String, Object> sortInfo);

    Integer checkBeforeDeleteSortName(Map<String, Object> sortData);

    Integer existNoSortName(Map<String, Object> sortData);

    Integer insertNoSortName(Map<String, Object> sortData);

    Integer updateSortNameForNoSortName(Map<String, Object> sortData);

    String getNoSortNameDate(Map<String, Object> sortData);

    Integer changeNoSortNameForAllDate(Map<String, Object> sortData);

    Integer noSortNameId(Map<String, Object> sortData);

    Map<String, Object> sortNameDeleteProcess(Map<String, Object> sortData);

    Map<String, Object> doModifyTransactionField(Map<String, Object> data);

    Map<String, Object> deleteTransactionField(Map<String, Object> data);

}
