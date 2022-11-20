package com.example.transaction.transactionQuestion;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleStaticPointCut extends StaticMethodMatcherPointcut {
    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
//                return clazz == TestClass.class;
                return false;
            }
        };
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return false; //"method2".equals(method.getName());
    }
}
