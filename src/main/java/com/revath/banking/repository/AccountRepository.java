package com.revath.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revath.banking.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

}