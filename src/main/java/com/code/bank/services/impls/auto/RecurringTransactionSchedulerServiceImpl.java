package com.code.bank.services.impls.auto;

import com.code.bank.services.interfaces.RecurringTransactionService;
import com.code.bank.services.interfaces.auto.RecurringTransactionSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RecurringTransactionSchedulerServiceImpl implements RecurringTransactionSchedulerService {

    private final RecurringTransactionService recurringTransactionService;

    public RecurringTransactionSchedulerServiceImpl(RecurringTransactionService recurringTransactionService) {
        this.recurringTransactionService = recurringTransactionService;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void runRecurringTransactions() {
        System.out.println("Bắt đầu kiểm tra giao dịch định kỳ...");
        recurringTransactionService.processRecurringTransactions();
        System.out.println("Hoàn thành kiểm tra giao dịch định kỳ.");
    }
}
