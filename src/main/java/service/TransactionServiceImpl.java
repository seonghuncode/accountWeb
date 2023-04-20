package service;

import controller.TransactionController;
import dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
