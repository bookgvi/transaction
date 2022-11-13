package com.example.transaction.controller;

import com.example.transaction.domain.Account;
import com.example.transaction.dto.sbp_alfa.SbpRegisterRequest;
import com.example.transaction.dto.TransferDTO;
import com.example.transaction.dto.sbp_alfa.SbpRegisterResponse;
import com.example.transaction.service.AccountService;
import com.example.transaction.service.RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@ResponseBody
@RequestMapping("/account")
public class AccountController {
    private final Logger logger = Logger.getLogger(AccountController.class.getName());
    private final AccountService accountService;
    private final RestClient restClient;

    public AccountController(AccountService accountService, RestClient restClient) {
        this.accountService = accountService;
        this.restClient = restClient;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.getAllRecords();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable long id) {
        Account acc = accountService.getById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(acc);
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<Account>> transferMoney(@RequestBody TransferDTO transfer) {
        List<Account> accounts = accountService.transferMoney(transfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(accounts);
    }

    @PostMapping("/register")
    public SbpRegisterResponse registerInSbp() throws JsonProcessingException {
        String paymentNum = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(12300);
        SbpRegisterRequest sbpRegisterDTO = new SbpRegisterRequest(paymentNum, amount);
        ObjectMapper mapper = new ObjectMapper();
        String responseString = restClient.register(
                sbpRegisterDTO.getOrderNumber(),
                sbpRegisterDTO.getAmount(),
                sbpRegisterDTO.getReturnUrl(),
                sbpRegisterDTO.getUserName(),
                sbpRegisterDTO.getPassword()
        );
        logger.info(responseString);
        return mapper.readValue(responseString, SbpRegisterResponse.class);
    }
}
