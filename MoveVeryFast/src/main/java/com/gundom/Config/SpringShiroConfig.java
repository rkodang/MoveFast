package com.gundom.Config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

@Configuration
public class SpringShiroConfig {
    /**
     * 会话管理对象,超时就会自动登出
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(60*10*1000);
        return defaultWebSessionManager;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        //记住我的Cookie的配置
        CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
        SimpleCookie cookie=new SimpleCookie("rememberMe");
        cookie.setMaxAge(7*24*60*60);
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }

    @Bean
    public CacheManager cacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean("MyManager")
    public SecurityManager newSecurityManager(SessionManager sessionManager, Realm realm, CacheManager cacheManager, RememberMeManager rememberMeManager){
        DefaultSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilterManager")//默认key为方法名
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(@Qualifier("MyManager") SecurityManager securityManager){
        //构建bean对象,通过此对象创建过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //注入SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登陆的URL
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
        //设置过滤请求-->LinkedHashMap为的是用来保证拦截顺序
        LinkedHashMap<String,String> filterRules=new LinkedHashMap<>();
        filterRules.put("/bower_components/**","anon");
        filterRules.put("/bulid/**","anon");
        filterRules.put("/dist/**","anon");
        filterRules.put("/plugins/**","anon");
        filterRules.put("/user/doLogin","anon");//不需要权限用anon
        filterRules.put("/doLogout","logout");//注销
//        filterRules.put("/**","authc");//需要认证
        filterRules.put("/**","user");//记住我功能

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRules);
        return shiroFilterFactoryBean;
    }

    //--授权配置相关----
    //配置Shiro框架中Bean对象的生命周期管理器
    @Bean //-->默认为方法名
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){

        return new LifecycleBeanPostProcessor();
    }

    /**
     * spring容器启动时,扫描所有的通知(Advisor)对象,基于切入点的描述,进行代理对象的创建
     * @return
     */
    @DependsOn("lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
    /**配置通知对象,在此对象中定义切入点以及要在切入点进行功能扩展  */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
