package service;

import controller.TransactionController;
import dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public int getPrimaryId(String userId){
        return transactionRepository.getPrimaryId(userId);
    }

    public Integer getTargetBudget(TransactionController.Transaction transaction){
        return transactionRepository.getTargetBudget(transaction);
    }

    public List<Map<String, Object>> getTransactionValue(TransactionController.Transaction transaction){
        return transactionRepository.getTransactionValue(transaction);
    }

    public List<Map<String, Object>> getTransactionHistory(TransactionController.Transaction transaction){
        return transactionRepository.getTransactionHistory(transaction);
    }

    public List<Map<String, Object>> getDistinctTransactionHistory(List<Map<String, Object>> transactionHistory){

        List<Map<String, Object>> distinctTransactionHistory = new ArrayList<Map<String, Object>>();
        Object date = "0000-00-00";
        for (Map<String, Object> map : transactionHistory) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
//                System.out.println("key: " + key + " | value: " + value);

                Map<String, Object> map1 = new HashMap<String, Object>();
                if (key.equals("transaction_date") && !(value.equals(date))) { //key값이 transaction_date이고 이미 나온 날짜가 아닐 경우
                    map1.put("transaction_date", value); //map1에 해당 날짜를 넣어 준다
//                    System.out.println("value : " + value);
                    date =  value;
                    //날짜를 distinctTransactionHistory에 담는다.
                    distinctTransactionHistory.add(map1);
                }
            }
        }
//        System.out.println(distinctTransactionHistory);


        return distinctTransactionHistory;
    }

}
