package com.mys.beans.factory.config;

/**
 * 管理单例Bean
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObj);

    String[] getSingletonNames();

}
