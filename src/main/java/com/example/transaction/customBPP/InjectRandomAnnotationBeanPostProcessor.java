package com.example.transaction.customBPP;

import com.example.transaction.annotations.InjectRandom;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InjectRandomAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandom annotation =  field.getAnnotation(InjectRandom.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                int rndInt = ThreadLocalRandom.current().nextInt(min, max);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, rndInt);
            }

        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
