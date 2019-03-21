package com.nork.collect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nork.collect.model.CollectionObject;
import com.nork.common.util.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class CollectUriAspect {
	
	private final static Logger LOGGER = LogManager.getLogger(CollectUriAspect.class);

	private static ObjectMapper mapper = new ObjectMapper();

	private static KafkaProducer kafkaProducer = getKafkaProducer();

	@Pointcut("@within(org.springframework.stereotype.Controller)")
	public void webRequest() {
	}

	private static KafkaProducer getKafkaProducer() {
		KafkaProducer kafkaProducer = SpringContextHolder.getBean("kafkaProducer");
		return kafkaProducer;
	}

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void restRequest() {
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Order(10)
	@Around("webRequest() || restRequest()")
	public Object doAround(ProceedingJoinPoint pjp) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String method = request.getMethod();

		boolean isProducer = false;
		long startTime = System.currentTimeMillis();
		CollectionObject.CollectionObjectBuilder obj = CollectionObject.builder();
		obj.startTime(Long.valueOf(startTime));

		List inputParams = new ArrayList();

		if ((HttpMethod.GET.matches(method)) || (HttpMethod.POST.matches(method)) || (HttpMethod.PUT.matches(method))
				|| (HttpMethod.DELETE.matches(method))) {
			isProducer = true;
			
			obj.host(request.getHeader("Host")).uri(request.getRequestURI()).method(method)
					.token(request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization"))
					.remoteAddress(getIp(request)).origin(request.getHeader("Origin"))
					.referfer(request.getHeader("Referer") == null ? "" : request.getHeader("Referer"))
					.userAgent(request.getHeader("User-Agent"))
					.platform(request.getHeader("Platform-Code") == null ? "" : request.getHeader("Platform-Code"))
					.params(request.getQueryString() == null ? "" : request.getQueryString());

			String contentType = request.getContentType();
			if ((StringUtils.isNotEmpty(contentType)) && (contentType.contains("application/json"))) {
				Object[] params = pjp.getArgs();
				int i = 0;
				for (int length = params.length; i < length; i++) {
					Object tmp = params[i];
					if (!(tmp instanceof BindingResult) && !(tmp instanceof RequestFacade) && !(tmp instanceof ResponseFacade)) {
						inputParams.add(tmp);
					}
				}
			}
		}

		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable throwable) {
			obj.statusCode(Integer.valueOf(500));
			LOGGER.error("Aop Proceed Error: " + throwable.getMessage());
		}

		long endTime = System.currentTimeMillis();
		obj.endTime(Long.valueOf(endTime));
		
		Integer taskType = (Integer) request.getAttribute("taskType");
		if(taskType != null) {
			obj.body("taskType=" + taskType);
		}
		
		if (isProducer) {
			if (inputParams.size() > 0) {
				try {
					obj.body(mapper.writeValueAsString(inputParams));
				} catch (JsonProcessingException e) {
					LOGGER.error("Request Body convert json error, " + e.getMessage());
				}
			}

			this.kafkaProducer.send(obj.build());
		}

		return result;
	}

	@Order(10)
	@AfterThrowing(value = "webRequest() || restRequest()", throwing = "ex")
	public void doAfterThrowing(Exception ex) {
		LOGGER.error("Throwing: " + ex.getMessage());
	}

	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}