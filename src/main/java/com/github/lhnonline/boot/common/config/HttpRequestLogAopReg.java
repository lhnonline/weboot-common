package com.github.lhnonline.boot.common.config;

import com.github.lhnonline.boot.common.aop.HttpRequestLogAop;
import com.github.lhnonline.boot.common.log.DefaultLogWriter;
import com.github.lhnonline.boot.common.log.ILogWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * author luohaonan
 * date 2020-11-11
 * email 0376lhn@gmail.com
 * description
 */
public class HttpRequestLogAopReg implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Class beanClass = HttpRequestLogAop.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanClass);
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        registry.registerBeanDefinition(beanName, beanDefinition);

        try {
            beanFactory.getBean(ILogWriter.class);
        } catch (BeansException e) {
            beanClass = DefaultLogWriter.class;
            beanDefinition = new RootBeanDefinition(beanClass);
            beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
