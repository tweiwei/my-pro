package com.mys.beans.factory;

import com.mys.beans.BeansException;
import com.mys.beans.config.BeanDefinition;
import com.mys.beans.support.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if(null == singleton){
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if(null == beanDefinition){
                throw new BeansException("No bean.");
            }

            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            this.registerSingleton(beanName, singleton);
        }

        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}
