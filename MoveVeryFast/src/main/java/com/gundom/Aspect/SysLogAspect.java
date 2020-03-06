package com.gundom.Aspect;

import com.gundom.Annoation.RequiredLog;
import com.gundom.Entity.SysLog;
import com.gundom.Entity.SysUser;
import com.gundom.Service.SysLogService;
import com.gundom.Utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 切入点的定义
 * 通知的定义(拓展功能)
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    /**
     * @Pointcut 注解用于描述或定义一个切入点
     */
    @Pointcut("@annotation(com.gundom.Annoation.RequiredLog)")
    public void logPointCut(){}

    /**
     * 环绕通知方法,用于添加业务逻辑,和目标方法
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint)throws Throwable{
        long start=System.currentTimeMillis();
        log.info("GetStarted:"+start);

        Object result = joinPoint.proceed();//调用下一个切面或者目标方法

        long end=System.currentTimeMillis();
        log.info("End:"+end);
        saveLog(joinPoint,(end-start));
        return result;
    }
    @Autowired
    private SysLogService sysLogService;
    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws Throwable {
        //1.获取字节码对象,通过字节码获取方法信息
        Class<?> joinPointClass = joinPoint.getTarget().getClass();
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取具体方法上的注解名称
        Method declaredMethod = joinPointClass.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        RequiredLog annotation = declaredMethod.getAnnotation(RequiredLog.class);
        String opreation = annotation.value();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        String params = Arrays.toString(args);

        //获取目标方法名
        String name = joinPointClass.getName();
        String name1 = signature.getName();
        String methodName=name+"."+name1;
        //封装参数
        Save2SysLog(opreation,methodName,params,time);

    }

    private void Save2SysLog(String opreation, String methodName, String params, long time) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SysLog sysLog=new SysLog();
        sysLog.setUsername(user.getUsername());
        sysLog.setIp(IPUtils.getIpAddr());
        sysLog.setOperation(opreation);
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setTime(time);
        sysLog.setCreatedTime(new Date());
        sysLogService.saveObject(sysLog);
        sysLog=null;
    }
}
