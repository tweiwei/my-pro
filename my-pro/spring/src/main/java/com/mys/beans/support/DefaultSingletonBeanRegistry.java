package com.mys.beans.support;

import com.mys.beans.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>();

    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObj) {
        synchronized (this.singletons){
            beanNames.add(beanName);
            singletons.put(beanName, singletonObj);
        }
    }

    @Override
    public String[] getSingletonNames() {
        return (String[])beanNames.toArray();
    }

    public void removeSingleton(String beanName){
        synchronized (this.singletons){
            beanNames.remove(beanName);
            singletons.remove(beanName);
        }
    }
}
