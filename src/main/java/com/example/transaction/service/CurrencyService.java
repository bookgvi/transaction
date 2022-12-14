package com.example.transaction.service;

import com.example.transaction.Exceptions.ResourceNotFoundException;
import com.example.transaction.domain.Currency;
import com.example.transaction.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Iterable<Currency> getAll() {
        return currencyRepository.findAll();
    }

    public Iterable<?> getAccountsByCurrency(long id) throws ResourceNotFoundException {
        Currency currency = currencyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Currence with id " + id + " not found..."));
        return currency.getAccounts();
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }
}
