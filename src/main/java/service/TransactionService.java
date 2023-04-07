package service;

import controller.TransactionController;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    int getPrimaryId(String userId);

    int getTargetBudget(TransactionController.Transaction transaction);

}
