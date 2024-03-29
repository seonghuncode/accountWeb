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

    int doAddTransactionHistory(TransactionController.AddTransactionHistory addTransactionHistory);

    Integer getSortNamePrimaryId(Map<String, Object> sortInfo);

    Integer checkBeforeDeleteSortName(Map<String, Object> sortData);

    Integer existNoSortName(Map<String, Object> sortData);

    Integer insertNoSortName(Map<String, Object> sortData);

    Integer updateSortNameForNoSortName(Map<String, Object> sortData);

    String getNoSortNameDate(Map<String, Object> sortData);

    Integer changeNoSortNameForAllDate(Map<String, Object> sortData);

    Integer noSortNameId(Map<String, Object> sortData);

    int doModifyTransactionField(Map<String, Object> data);

    int deleteTransactionField(Map<String, Object> data);

    Map<String, Object> getUserInfo(int primaryId);

    List<Map<String, Object>> getTransactionSumBySortName(Map<String, Object> data);

    Integer getTotalPrice(Map<String, Object> data);

    List<Map<String, Object>> getTransactionSumBySortNameAndPeriod(Map<String, Object> data);

    Integer getTargetBudgeByPeriod(Map<String, Object> data);

    Integer getTotalPriceByPeriod(Map<String, Object> data);

    //기간별 거래내역 검색의 경우 해당 하는 기간의 목표예산을 전부 합해주는 로직
    Integer periodTotalBudget(TransactionController.Transaction transaction);

    //기간별 거래내역 검색의 경우 해당 기간에 해당 하는 값들을 가지고 오는 로직
    List<Map<String, Object>> getTransactionValueByPeriod(TransactionController.Transaction transaction);


}
