package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.registry;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExtractorsBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Extractor) {
            ExtractorService.registerService(((Extractor) bean).getKey(), (Extractor) bean);
        }
        return bean;
    }

}
