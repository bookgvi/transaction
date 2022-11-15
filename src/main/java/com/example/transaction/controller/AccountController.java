package com.example.transaction.controller;

import com.example.transaction.Exceptions.ResourceNotFoundException;
import com.example.transaction.domain.Account;
import com.example.transaction.domain.SbpRegisterResponse;
import com.example.transaction.dto.TransferDTO;
import com.example.transaction.dto.sbp_alfa.SbpRegisterRequest;
import com.example.transaction.service.AccountService;
import com.example.transaction.service.RestClient;
import com.example.transaction.service.SbpResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final AccountService accountService;
    private final RestClient restClient;
    private final SbpResponseService sbpResponseService;

    public AccountController(AccountService accountService, RestClient restClient, SbpResponseService sbpResponseService) {
        this.accountService = accountService;
        this.restClient = restClient;
        this.sbpResponseService = sbpResponseService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Account>> getAccounts() {
        Iterable<Account> accounts = accountService.getAllRecords();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accounts);
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

    private Optional<Account> validateAndGetAccountById(long id) throws ResourceNotFoundException {
        Optional<Account> acc = accountService.getById(id);
        if (acc.isEmpty()) {
            throw new ResourceNotFoundException("Account with id " + id + " not found");
        }
        return acc;
    }
}
