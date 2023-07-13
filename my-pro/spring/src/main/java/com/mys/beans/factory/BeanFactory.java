package com.mys.beans.factory;

import com.mys.beans.BeansException;
import com.mys.beans.factory.config.BeanDefinition;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String beanName);

    void registerBean(String beanName, Object obj);

    void registerBeanDefinition(BeanDefinition beanDefinition);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}
