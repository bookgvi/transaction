package com.example.transaction.repository;

import com.example.transaction.domain.SbpRegisterResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SbpResponseRepository extends CrudRepository<SbpRegisterResponse, Long> {
    Iterable<SbpRegisterResponse> findAll();
}
