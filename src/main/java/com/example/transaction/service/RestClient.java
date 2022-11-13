package com.example.transaction.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "sbpAlfa", url = "https://web.rbsuat.com/ab/rest/register.do")
public interface RestClient {
    @PostMapping
    public String register(
            @RequestParam String orderNumber,
            @RequestParam BigDecimal amount,
            @RequestParam(defaultValue = "http://return.url") String returnUrl,
            @RequestParam(defaultValue = "rgs_0-api") String userName,
            @RequestParam(defaultValue = "rgs_0*?1") String password
    );
}
