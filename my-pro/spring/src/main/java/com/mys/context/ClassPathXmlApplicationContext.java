package com.mys.context;

import com.mys.beans.BeansException;
import com.mys.beans.factory.SimpleBeanFactory;
import com.mys.beans.factory.config.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
//    private Map<String, Object> singletons = new HashMap<>();

    private SimpleBeanFactory beanFactory = new SimpleBeanFactory();

    public ClassPathXmlApplicationContext(String fileName){
        this.readXml(fileName);
        this.instanceBeans();
    }

    private void readXml(String fileName){
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            for(Element element : (List<Element>)rootElement.elements()){
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

                this.beanFactory.registerBeanDefinition(beanDefinition);
                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void instanceBeans(){
        for(BeanDefinition beanDefinition : beanDefinitions){
            try {
                this.beanFactory.registerBean(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    public Boolean containsBean(String name){
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj){
        this.beanFactory.registerBean(beanName, obj);
    }
}
