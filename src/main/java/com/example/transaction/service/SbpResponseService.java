package com.example.transaction.service;

import com.example.transaction.domain.SbpRegisterResponse;
import com.example.transaction.dto.sbp_alfa.SbpRegisterRequest;
import com.example.transaction.repository.SbpResponseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class SbpResponseService {
    private final SbpResponseRepository repository;

    public SbpResponseService(SbpResponseRepository repository) {
        this.repository = repository;
    }

    public Iterable<SbpRegisterResponse> getAll() {
        return repository.findAll();
    }

    public void save(SbpRegisterResponse registerResponse) {
        repository.save(registerResponse);
    }

    public SbpRegisterRequest prepareRegisterRequest() {
        String paymentNum = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(12300);
        SbpRegisterRequest sbpRegisterDTO = new SbpRegisterRequest(paymentNum, amount);
        return sbpRegisterDTO;
    }

    public SbpRegisterResponse saveRegisterResponse(String responseFromSbp, SbpRegisterRequest sbpRegisterDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SbpRegisterResponse registerResponse = mapper.readValue(responseFromSbp, SbpRegisterResponse.class);
        registerResponse.setOrderNumber(sbpRegisterDTO.getOrderNumber());
        save(registerResponse);
        return registerResponse;
    }
}
