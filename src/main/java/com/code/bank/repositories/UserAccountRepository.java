package com.code.bank.repositories;

import com.code.bank.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String phone);
    boolean existsByUsername(String phone);
}