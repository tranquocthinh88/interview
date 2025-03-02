package com.code.bank.services.impls;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.models.Account;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.repositories.TransactionRepository;
import com.code.bank.services.interfaces.TransactionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, String> implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private static final double FEE = 1000.0;
    private static final double DEPOSIT_FEE = 0.0;

    public TransactionServiceImpl(JpaRepository<Transaction, String> repository,
                                  TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        super(repository, Transaction.class);
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction createTransaction(String accountNumber, TransactionDto transactionDto) {
        Account senderAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        double transactionFee = getTransactionFee(transactionDto.getTransactionType());
        double totalAmount = transactionDto.getAmount() + transactionFee;

        // Nếu là chuyển khoản, cần kiểm tra tài khoản nhận
        Account receiverAccount = null;
        if (transactionDto.getTransactionType() == TransactionType.TRANSFER) {
            if (transactionDto.getReceiverAccountNumber() == null) {
                throw new RuntimeException("Receiver account ID must be provided for transfers");
            }

            receiverAccount = accountRepository.findByAccountNumber(transactionDto.getReceiverAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Receiver account not found"));

            // Kiểm tra số dư tài khoản gửi
            if (totalAmount + 50000 > senderAccount.getBalance()) {
                throw new RuntimeException("Insufficient balance for transfer!");
            }

            if (totalAmount > senderAccount.getTransactionLimit()) {
                throw new RuntimeException("Transaction exceeds limit!");
            }

            // Trừ tiền từ tài khoản gửi
            senderAccount.setBalance(senderAccount.getBalance() - totalAmount);

            // Cộng tiền vào tài khoản nhận
            receiverAccount.setBalance(receiverAccount.getBalance() + transactionDto.getAmount());
        } else if (transactionDto.getTransactionType() == TransactionType.WITHDRAWAL) {
            if (totalAmount + 50000 > senderAccount.getBalance()) {
                throw new RuntimeException("Insufficient balance for withdrawal!");
            }
            if (totalAmount > senderAccount.getTransactionLimit()) {
                throw new RuntimeException("Transaction exceeds limit!");
            }
            senderAccount.setBalance(senderAccount.getBalance() - (transactionDto.getAmount() + transactionFee));
        } else if (transactionDto.getTransactionType() == TransactionType.DEPOSIT) {
            senderAccount.setBalance(senderAccount.getBalance() + transactionDto.getAmount());
        }

        // Tạo giao dịch
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setAccount(senderAccount);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setFee(transactionFee);
        transaction.setLocation(transactionDto.getLocation());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReceiverAccount(transactionDto.getReceiverAccountNumber());

        // Lưu giao dịch và cập nhật tài khoản
        transactionRepository.save(transaction);
        accountRepository.save(senderAccount);
        if (receiverAccount != null) {
            accountRepository.save(receiverAccount);
        }

        return transaction;
    }

    private double getTransactionFee(TransactionType transactionType) {
        return switch (transactionType) {
            case WITHDRAWAL , TRANSFER -> FEE;
            case DEPOSIT -> DEPOSIT_FEE;
            default -> throw new IllegalArgumentException("Invalid transaction type");
        };
    }
}
