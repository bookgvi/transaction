package com.example.transaction.transactionQuestion;

import com.example.transaction.annotations.MyTransaction;
import org.springframework.stereotype.Component;

@Component
public class TestClass1 {
    @MyTransaction
    public void method1() {
        System.out.println("TestClass1#method1()");
        method2();
    }

    @MyTransaction
    public void method2() {
        System.out.println("TestClass1#method2()");
    }
}
