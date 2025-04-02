package com.code.bank.repositories.customizations;

import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> filterTransactions(
            BigDecimal minAmount, BigDecimal maxAmount, TransactionType transactionType, LocalDate fromDate, LocalDate toDate) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minAmount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }
            if (maxAmount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }
            if (transactionType != null) {
                predicates.add(criteriaBuilder.equal(root.get("transactionType"), transactionType));
            }
            if (fromDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), fromDate));
            }
            if (toDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), toDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
