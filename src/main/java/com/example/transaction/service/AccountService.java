package com.example.transaction.service;

import com.example.transaction.domain.Account;
import com.example.transaction.dto.TransferDTO;
import com.example.transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllRecords() {
        return accountRepository.findAll();
    }

    public Account getById(long id) {
        return accountRepository.findAccountById(id);
    }

    @Transactional
    public List<Account> transferMoney(TransferDTO transfer) {
        BigDecimal amt = transfer.getTransferAmount();
        Account srcAcc = getById(transfer.getSourceAccountId());
        Account dstAcc = getById(transfer.getDestAccountId());

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

    @Transactional
    @Deprecated
    public List<Account> oldtransferMoney(TransferDTO transfer) {
        BigDecimal amt = transfer.getTransferAmount();
        Account srcAcc = accountRepository.getById(transfer.getSourceAccountId());
        Account dstAcc = accountRepository.getById(transfer.getDestAccountId());

        BigDecimal srcAccNewAmount = srcAcc.getAmount_amt().subtract(amt);
        BigDecimal dstAccNewAmount = dstAcc.getAmount_amt().add(amt);
        accountRepository.changeAmount(transfer.getSourceAccountId(), srcAccNewAmount);
        accountRepository.changeAmount(transfer.getDestAccountId(), dstAccNewAmount);

        Account newSrcAcc = accountRepository.getById(transfer.getSourceAccountId());
        Account newDstAcc = accountRepository.getById(transfer.getDestAccountId());
        return List.of(newSrcAcc, newDstAcc);
    }


}
