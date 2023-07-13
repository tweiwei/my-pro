package com.mys.test;

import com.mys.beans.BeansException;
import com.mys.context.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }

}
