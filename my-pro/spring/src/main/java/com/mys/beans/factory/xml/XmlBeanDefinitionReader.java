package com.mys.beans.factory.xml;

import com.mys.beans.factory.SimpleBeanFactory;
import com.mys.beans.factory.config.BeanDefinition;
import com.mys.core.Resource;
import org.dom4j.Element;

import java.util.List;

public class XmlBeanDefinitionReader {
    SimpleBeanFactory beanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            List<Element> propertyElements = element.elements("property");


            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
