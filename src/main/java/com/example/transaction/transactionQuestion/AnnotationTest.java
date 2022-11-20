package com.example.transaction.transactionQuestion;

import com.example.transaction.annotations.InjectRandom;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AnnotationTest {
    @InjectRandom(min = 2, max = 10)
    private int rnd;

    @PostConstruct
    void init() {
        System.out.println(rnd);
    }
}
