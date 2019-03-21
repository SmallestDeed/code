package com.sandu.interceptor;

import com.sandu.annotation.DuplicateSubmitToken;
import com.sandu.exception.DuplicateSubmitException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author chenqiang
 * @Description 防止表单重复提交拦截器
 * @Date 2018/6/26 0026 20:10
 * @Modified By
 */
@Aspect
@Component
@Slf4j
public class DuplicateSubmitAspect {

    public static final String  DUPLICATE_TOKEN_KEY="duplicate_token_key";

    @Pointcut("execution(public * com.sandu.web..*.*(..))")
    public void webLog() { }


    @Before("webLog() && @annotation(token)")
    public void before(final JoinPoint joinPoint, DuplicateSubmitToken token){
        if (token!=null){
            log.info("方法开始 添加标记 start");
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            boolean isSaveSession = token.save();
            if (isSaveSession){
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null==t){
                    String uuid= UUID.randomUUID().toString();
                    request.getSession().setAttribute(key.toString(),uuid);
                    log.info("方法开始 token-key="+key);
                    log.info("方法开始 token-value="+uuid.toString());
                }else {
//                    throw new DuplicateSubmitException(TextConstants.REQUEST_REPEAT);
                    throw new DuplicateSubmitException("请勿重复提交");
                }
            }
            log.info("方法开始 添加标记 end");
        }
    }


    @AfterReturning("webLog() && @annotation(token)")
    public void doAfterReturning(JoinPoint joinPoint,DuplicateSubmitToken token) {
        log.info("方法返回 移除标记 start");
        if (token!=null){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            boolean isSaveSession = token.save();
            if (isSaveSession){
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null!=t&&token.type()==DuplicateSubmitToken.REQUEST){
                    request.getSession(false).removeAttribute(key);
                }
            }
        }
        log.info("方法返回 移除标记 end");
    }


    /**
     * 获取重复提交key
     * @param joinPoint
     * @return
     */
    public String getDuplicateTokenKey(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder key=new StringBuilder(DUPLICATE_TOKEN_KEY);
        key.append(",").append(methodName);
        return key.toString();
    }


    /**
     * 异常
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webLog()&& @annotation(token)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e, DuplicateSubmitToken token) {
        log.info("方法出现异常，移除标记 start");
        if (null!=token
                && e instanceof DuplicateSubmitException==false){
            //处理处理重复提交本身之外的异常
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            boolean isSaveSession=token.save();
            //获得方法名称
            if (isSaveSession){
                String key=getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null!=t){
                    //方法执行完毕移除请求重复标记
                    request.getSession(false).removeAttribute(key);
                    log.info("异常情况--移除标记！");
                }
            }
        }
        log.info("方法出现异常，移除标记 end");
    }
}