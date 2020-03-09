package com.gundom.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class SpringAsyncConfig {
    private int corePoolSize=10;
    private int maximumPoolSize=20;
    private int keepAliveTime=30;
    private int queueCapacity=20;
    private ThreadFactory threadFactory=new ThreadFactory() {
        private AtomicLong number=new AtomicLong(100000);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"async-thread"+number.getAndIncrement());
        }
    };

    @Bean("AsyncThreadPool")
    public ThreadPoolExecutor newThreadPoolExecutor(){
        ThreadPoolExecutor pool=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,
                TimeUnit.SECONDS,new LinkedBlockingDeque<>(queueCapacity),threadFactory);

        return pool;
    }
}
