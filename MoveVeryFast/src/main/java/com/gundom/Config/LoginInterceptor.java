package com.gundom.Config;

import com.gundom.Exception.ServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Calendar timeUtils = Calendar.getInstance();
        timeUtils.set(Calendar.HOUR_OF_DAY,9);
        timeUtils.set(Calendar.MINUTE,0);
        timeUtils.set(Calendar.SECOND,0);
        long start=timeUtils.getTimeInMillis();
        timeUtils.set(Calendar.HOUR_OF_DAY,21);
        long end=timeUtils.getTimeInMillis();
        long loginTime=System.currentTimeMillis();
        if(loginTime<start || loginTime>end){
            throw new ServiceException("已超过工作时间");
        }
        return true;
    }
}
