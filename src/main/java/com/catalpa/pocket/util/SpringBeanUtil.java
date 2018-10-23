package com.catalpa.pocket.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <pre>
 * 使用时需要：
 * 1. 设置scanBasePackages = "com.example.springboot"</li>
 * 2. 在使用的类加 @DependsOn("springBeanUtil")</li>
 * </pre>
 * Created by bruce on 2018/4/17.
 */
@Component
public class SpringBeanUtil {

    private static SpringBeanUtil instance;

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringBeanUtil(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    public static Object getBean(String name) throws BeansException {
        return instance.applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return instance.applicationContext.getBean(name, clazz);
    }

    public static Object getBean(String name, Object... objects) throws BeansException {
        return instance.applicationContext.getBean(name, objects);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return instance.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(Class<T> clazz, Object... objects) throws BeansException {
        return instance.applicationContext.getBean(clazz, objects);
    }
}
