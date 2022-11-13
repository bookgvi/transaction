package com.example.transaction.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {
    Class<? extends Enum<?>> enumClass();
}
