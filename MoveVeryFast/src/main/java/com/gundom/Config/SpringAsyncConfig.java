package com.gundom.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {
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

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor pool=new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maximumPoolSize);
        pool.setKeepAliveSeconds(keepAliveTime);
        pool.setQueueCapacity(queueCapacity);
        pool.setThreadFactory(threadFactory);
        //设置拒绝执行的处理
        pool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("队列已满,拒绝执行");
            }
        });
        pool.initialize();

        return pool;
    }
}
