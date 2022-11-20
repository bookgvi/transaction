package com.example.transaction.controller;

import com.example.transaction.Exceptions.ResourceNotFoundException;
import com.example.transaction.domain.Account;
import com.example.transaction.domain.Currency;
import com.example.transaction.domain.SbpRegisterResponse;
import com.example.transaction.dto.TransferDTO;
import com.example.transaction.dto.sbp_alfa.SbpRegisterRequest;
import com.example.transaction.service.AccountService;
import com.example.transaction.service.CurrencyService;
import com.example.transaction.service.RestClient;
import com.example.transaction.service.SbpResponseService;
import com.example.transaction.annotations.MyTransaction;
import com.example.transaction.transactionQuestion.TestClass1;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@ResponseBody
@RequestMapping("/account")
public class AccountController {
    private final Logger logger = Logger.getLogger(AccountController.class.getName());
    @Autowired
    private AccountService accountService;
    @Autowired
    private RestClient restClient;
    @Autowired
    private SbpResponseService sbpResponseService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private TestClass1 testClass;

    @GetMapping
    @MyTransaction
    public ResponseEntity<Iterable<Account>> getAccounts() {
        testClass.method1();
        Iterable<Account> accounts = accountService.getAllRecords();
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable long id) {
        Optional<Account> acc = validateAndGetAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body(acc);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Iterable<Account>> transferMoney(@RequestBody TransferDTO transfer) throws AccountNotFoundException {
        Iterable<Account> accounts = accountService.transferMoney(transfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(accounts);
    }

    @PostMapping("/register")
    public SbpRegisterResponse registerInSbp() throws JsonProcessingException {
        SbpRegisterRequest sbpRegisterDTO = sbpResponseService.prepareRegisterRequest();
        String responseString = restClient.register(
                sbpRegisterDTO.getOrderNumber(),
                sbpRegisterDTO.getAmount(),
                sbpRegisterDTO.getReturnUrl(),
                sbpRegisterDTO.getUserName(),
                sbpRegisterDTO.getPassword()
        );
        logger.info(responseString);
        return sbpResponseService.saveRegisterResponse(responseString, sbpRegisterDTO);
    }

    @PostMapping("/add")
    public Account addAccount(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PostMapping("/currency")
    public ResponseEntity<Iterable<?>> getAccountsWithCurrency(@RequestBody Currency currency) throws ResourceNotFoundException {
        long id = currency.getId();
        Iterable<?> accounts = currencyService.getAccountsByCurrency(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(accounts);
    }

    private Optional<Account> validateAndGetAccountById(long id) throws ResourceNotFoundException {
        Optional<Account> acc = accountService.getById(id);
        if (acc.isEmpty()) {
            throw new ResourceNotFoundException("Account with id " + id + " not found");
        }
        return acc;
    }
}
