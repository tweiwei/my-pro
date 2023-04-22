package com.mys.beans.factory;

import com.mys.beans.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String beanName);

    void registerBean(String beanName, Object obj);

}
