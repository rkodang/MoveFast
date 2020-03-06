package com.gundom.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;


@Configuration
public class SpringWebConfig {

    @Bean
    public FilterRegistrationBean newFilterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        DelegatingFilterProxy filterProxy=new DelegatingFilterProxy("shiroFilterManager");
        filterRegistrationBean.setFilter(filterProxy);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
