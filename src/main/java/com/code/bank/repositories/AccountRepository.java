package com.code.bank.repositories;

import com.code.bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends BaseRepository<Account, Integer> {
}