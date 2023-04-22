package com.mys.beans.config;

public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObj);

    String[] getSingletonNames();

}
