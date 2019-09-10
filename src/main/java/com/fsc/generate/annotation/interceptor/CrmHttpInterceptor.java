package com.fsc.generate.annotation.interceptor;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.dto.BasicRequest;
import com.fsc.generate.model.dto.BasicResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Aspect
@Service
public class CrmHttpInterceptor {

    private Logger logger = LoggerFactory.getLogger(CrmHttpInterceptor.class);

    @Pointcut("@annotation(com.fsc.generate.annotation.ProcessRequestAndResponse)")
    public void processRequestAndResponse() {
    }

    @Around("com.fsc.generate.annotation.interceptor.CrmHttpInterceptor.processRequestAndResponse()")
    public Object doProcessRequestAndResponse(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getRequest(pjp);
        HttpServletResponse response = getResponse(pjp);
        if (Objects.isNull(request) || Objects.isNull(response)) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_INVALID_ARGS);
        }
        BasicRequest requestArgs = getRequestArgs(pjp);
        try {
            if (Objects.nonNull(requestArgs)) {
                logger.info("[doProcessRequestAndResponse] [req] [{}]", requestArgs);
            }
            Object proceed = pjp.proceed();
            if (proceed instanceof BasicResponse) {
                logger.info("[doProcessRequestAndResponse] [resp] [{}]", proceed);
            }
            return proceed;
        } catch (Throwable throwable) {
            if (throwable instanceof CrmException) {
                CrmException crmException = (CrmException) throwable;
                logger.error("[doProcessRequestAndResponse] [ERROR] [{}-{}-{}] HTTP CODE[{}]", crmException.getRetCode(),
                        crmException.getErrCode(), crmException.getMessage(), 500, crmException);
                BasicResponse basicResponse = new BasicResponse(crmException.getRetCode(),
                        crmException.getErrCode(), crmException.getMessageCn(), crmException.getMessageEn());
                logger.info("[doProcessRequestAndResponse] [resp] [{}]", basicResponse);
                return basicResponse;
            } else {
                logger.error("[doProcessRequestAndResponse] [ERROR] ", throwable);
            }
            throw throwable;
        }
    }

    private BasicRequest getRequestArgs(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof BasicRequest) {
                return (BasicRequest) args[i];
            }
        }
        return null;
    }

    private HttpServletRequest getRequest(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                return (HttpServletRequest) args[i];
            }
        }
        return null;
    }

    private HttpServletResponse getResponse(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletResponse) {
                return (HttpServletResponse) args[i];
            }
        }
        return null;
    }

}
