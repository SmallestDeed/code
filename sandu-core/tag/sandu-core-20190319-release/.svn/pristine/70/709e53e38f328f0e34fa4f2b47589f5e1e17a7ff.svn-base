package com.sandu.service.springFestivalActivity.aspect;

import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.springFestivalActivity.api.NonNullPropoties;
import com.sandu.api.springFestivalActivity.api.SendTemplateMsgAble;
import com.sandu.api.springFestivalActivity.model.SendTemplateMsg;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class SpringActivityAspect {
    private static final String CLASS_LOG_PREFIX = "[参数校验服务]";
    private final TemplateMsgService templateMsgService;

    @Autowired
    public SpringActivityAspect(TemplateMsgService templateMsgService) {
        this.templateMsgService = templateMsgService;
    }

    @Pointcut("@annotation(com.sandu.service.springFestivalActivity.aspect.CheckMethodArgs)")
    public void checkArgs() {
    }

    @Pointcut("@annotation(com.sandu.service.springFestivalActivity.aspect.SendTemplateMessage)")
    public void sendTemplateMessage() {
    }

    @Before("checkArgs()")
    public void interceptor(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg == null) {
                    log.warn(CLASS_LOG_PREFIX + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName() + ":参数异常!\n" + Arrays.toString(point.getArgs()));
                    throw new BizException("参数异常");
                } else {
                    if (arg instanceof NonNullPropoties) {
                        if (!((NonNullPropoties) arg).nonNull()) {
                            log.warn(CLASS_LOG_PREFIX + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName() + ":参数异常!\n" + Arrays.toString(point.getArgs()));
                            throw new BizException("参数异常");
                        }
                    }
                }
            }
        }
    }

    @AfterReturning(returning = "rt", pointcut = "sendTemplateMessage()")
    public void sendMsg(Object rt) {
        if (rt instanceof SendTemplateMsgAble) {
            if (((SendTemplateMsgAble) rt).sendMsg() != null) {
                try {
                    SendTemplateMsg msg = ((SendTemplateMsgAble) rt).sendMsg();
                    TemplateMsgReqParam param = templateMsgService.buildTemplateReqParam(msg.getUser(), msg.getMsgType(), msg.getTemplateData(), msg.getPageParams());
                    templateMsgService.sendTemplateMsg(param);
                } catch (Exception e) {
                    log.error(CLASS_LOG_PREFIX + e.getMessage(), e);
                }
            }
        }
    }
}
