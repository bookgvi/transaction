package com.example.transaction.service;

import com.example.transaction.domain.Account;
import com.example.transaction.dto.TransferDTO;
import com.example.transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAllRecords() {
        return accountRepository.findAll();
    }

    public Optional<Account> getById(long id) {
        return accountRepository.getById(id);
    }

    @Transactional
    public Iterable<Account> transferMoney(TransferDTO transfer) throws AccountNotFoundException {
        BigDecimal amt = transfer.getTransferAmount();
        Optional<Account> srcAccOpt = getById(transfer.getSourceAccountId());
        Optional<Account> dstAccOpt = getById(transfer.getDestAccountId());

        Account srcAcc = srcAccOpt.orElseThrow(AccountNotFoundException::new);
        Account dstAcc = dstAccOpt.orElseThrow(AccountNotFoundException::new);
        BigDecimal srcAccNewAmount = srcAcc.getAmount_amt().subtract(amt);
        BigDecimal dstAccNewAmount = dstAcc.getAmount_amt().add(amt);
        if (srcAccNewAmount.compareTo(BigDecimal.ZERO) < 0 || dstAccNewAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Negative...");
        }
        srcAcc.setAmount_amt(srcAccNewAmount);
        dstAcc.setAmount_amt(dstAccNewAmount);

        List<Account> accounts = List.of(srcAcc, dstAcc);
        accountRepository.save(srcAcc);
        accountRepository.saveAll(accounts);
        return accounts;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
