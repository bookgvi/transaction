package com.example.transaction.controller;

import com.example.transaction.domain.SbpRegisterResponse;
import com.example.transaction.service.SbpResponseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/sbp")
public class SbpResponseController {
    private final SbpResponseService sbpResponseService;

    public SbpResponseController(SbpResponseService sbpResponseService) {
        this.sbpResponseService = sbpResponseService;
    }

    @GetMapping("{register}")
    Iterable<SbpRegisterResponse> getAllRegisters() {
        return sbpResponseService.getAll();
    }
}
