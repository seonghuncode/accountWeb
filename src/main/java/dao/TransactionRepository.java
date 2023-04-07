package dao;

import controller.TransactionController;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository {

    int getPrimaryId(String userId);

    int getTargetBudget(TransactionController.Transaction transaction);
}
