package cn.edu.bjut.reggie.aop;

import cn.edu.bjut.reggie.entity.OperateLog;
import cn.edu.bjut.reggie.service.OperateLogService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Vector;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogService operateLogService;

    @Around("@annotation(cn.edu.bjut.reggie.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long UserId = (Long) request.getSession().getAttribute("employee");

        LocalDateTime operateTime = LocalDateTime.now();

        String className = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        String resultValue = JSONObject.toJSONString(result);

        long costTime = end - begin;

        OperateLog operateLog = new OperateLog();
        operateLog.setOperateUser(UserId);
        operateLog.setOperateTime(operateTime);
        operateLog.setClassName(className);
        operateLog.setMethodName(methodName);
        operateLog.setMethodParams(methodParams);
        operateLog.setReturnValue(resultValue);
        operateLog.setCostTime(costTime);

        operateLogService.save(operateLog);
        return result;

    }
}
