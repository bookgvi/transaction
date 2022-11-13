package com.example.transaction.repository;

import com.example.transaction.domain.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAll();

    @Query(value = "select * " +
            "from account acc " +
            "where 1 = 1 " +
            "and acc.id = :id", nativeQuery = true)
    Account findAccountById(long id);

    @Query(value = "SELECT * FROM account", nativeQuery = true)
    List<Object> myFindAll();

    @Query(value = "select * from account acc " +
            "where 1 = 1 " +
            "and acc.id = :id", nativeQuery = true)
    Account getById(long id);

    @Modifying
    @Query(value = "update account acc set acc.amount_amt = :amount " +
            "where 1 = 1 " +
            "and acc.id = :id", nativeQuery = true)
    void changeAmount(long id, BigDecimal amount);


}
