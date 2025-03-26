package com.code.bank.services.impls;

import com.code.bank.api.dtos.requests.RecurringTransactionDto;
import com.code.bank.api.mappers.RecurringTransactionMapper;
import com.code.bank.models.Account;
import com.code.bank.models.RecurringTransaction;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.repositories.RecurringTransactionRepository;
import com.code.bank.repositories.TransactionRepository;
import com.code.bank.services.interfaces.RecurringTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    private final AccountRepository accountRepository;
    private final RecurringTransactionMapper recurringTransactionMapper;
    private final RecurringTransactionRepository recurringTransactionRepository;
    private final TransactionRepository transactionRepository;

    public RecurringTransactionServiceImpl(AccountRepository accountRepository,
                                           RecurringTransactionMapper recurringTransactionMapper,
                                           RecurringTransactionRepository recurringTransactionRepository,
                                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.recurringTransactionMapper = recurringTransactionMapper;
        this.recurringTransactionRepository = recurringTransactionRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecurringTransaction createRecurringTransaction(RecurringTransactionDto recurringTransactionDto) {

        Account account = accountRepository.findById(recurringTransactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        RecurringTransaction recurringTransaction = recurringTransactionMapper
                .recurringTransactionDtoToRecurringTransaction(recurringTransactionDto, account);
        recurringTransaction.setAccount(account);
        return recurringTransactionRepository.save(recurringTransaction);
    }

    @Override
    @Transactional
    public void processRecurringTransactions() {
        List<RecurringTransaction> recurringTransactions = recurringTransactionRepository.findByIsActiveTrue();
        LocalDate today = LocalDate.from(LocalDateTime.now());

        for (RecurringTransaction recurring : recurringTransactions) {
            LocalDateTime nextExecutionDate = calculateNextExecution(
                    LocalDate.from(recurring.getStartDate()), today).atStartOfDay();
            // Nếu hôm nay là ngày cần thực hiện giao dịch
            if (nextExecutionDate.toLocalDate().equals(today)) {
                try {
                    Account account = recurring.getAccount();
                    double amount = recurring.getAmount();

                    Transaction transaction = new Transaction();
                    transaction.setAccount(account);
                    transaction.setTransactionType(recurring.getTransactionType());
                    transaction.setAmount(recurring.getAmount());
                    transaction.setTransactionDate(LocalDateTime.now());
                    transaction.setLocation("System");

                    if(recurring.getTransactionType() == TransactionType.DEPOSIT) {
                        account.setBalance(account.getBalance() + amount);
                        transaction.setFee(0.0);
                    }
                    else
                        if(recurring.getTransactionType() == TransactionType.TRANSFER) {
                            if (account.getBalance() > amount ) {
                                account.setBalance(account.getBalance() - amount);
                                transaction.setReceiverAccountNumber(recurring.getReceiverAccount());
                                transaction.setFee(1000);
                            } else {
                                System.out.println("Không đủ số dư cho giao dịch: " + recurring.getId());
                            }

                        }
                    transactionRepository.save(transaction);
                    accountRepository.save(account);
                } catch (Exception e) {
                    System.err.println("Lỗi khi xử lý giao dịch định kỳ: " + e.getMessage());
                }
            }
        }
    }

    private LocalDate calculateNextExecution(LocalDate startDate, LocalDate today) {
        LocalDate nextExecution = startDate;
        while (nextExecution.isBefore(today) || nextExecution.isEqual(today)) {
            nextExecution = nextExecution.plusMonths(1); // Cộng thêm 1 tháng
        }
        return nextExecution;
    }
}
