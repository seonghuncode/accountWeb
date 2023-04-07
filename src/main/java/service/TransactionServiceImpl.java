package service;

import controller.TransactionController;
import dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public int getPrimaryId(String userId){
        return transactionRepository.getPrimaryId(userId);
    }

    public int getTargetBudget(TransactionController.Transaction transaction){
        return transactionRepository.getTargetBudget(transaction);
    }
}
