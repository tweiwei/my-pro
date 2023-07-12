package com.mys.beans.factory.xml;

import com.mys.beans.factory.config.BeanDefinition;
import com.mys.beans.factory.BeanFactory;
import com.mys.core.Resource;
import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
