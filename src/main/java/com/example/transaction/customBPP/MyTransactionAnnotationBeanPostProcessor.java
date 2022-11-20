package com.example.transaction.customBPP;

import com.example.transaction.annotations.MyTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyTransactionAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Logger logger = LoggerFactory.getLogger(MyTransactionAnnotationBeanPostProcessor.class);
    private final Map<String, Class<?>> annotatedBeans = new ConcurrentHashMap<>(256);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Arrays.stream(bean.getClass().getMethods()).anyMatch(method -> method.isAnnotationPresent(MyTransaction.class))) {
            annotatedBeans.put(beanName, bean.getClass());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> annotatedClass = annotatedBeans.get(beanName);
        if (annotatedClass != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(annotatedClass);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    Optional<Method> annotatedMethod = Arrays.stream(annotatedClass.getMethods()).filter(method::equals).findFirst();
                    if (annotatedMethod.isPresent()) {
                        MyTransaction myTransaction = annotatedMethod.get().getAnnotation(MyTransaction.class);
                        if (myTransaction != null) {
                            logger.info("QQQ " + method.getName());
                        }
                    }
                    return method.invoke(bean, objects);
                }
            });
            return enhancer.create();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}