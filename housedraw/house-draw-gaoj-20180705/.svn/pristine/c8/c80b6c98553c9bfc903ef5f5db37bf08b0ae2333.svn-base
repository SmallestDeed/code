package com.sandu.mail;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sandu.api.mail.model.PerfectHouseMailMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.mail.service.TemplateMailService;
import com.sandu.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年4月3日
 */

@Slf4j
@Service
public class PerfectHouseTemplateMailServiceImpl implements TemplateMailService {

	@Value("${spring.mail.username}")
	private String from;
	
	@Value("${mail.perfect.house.subject}")
	private String subject;

	@Value("${mail.perfect.house.message}")
	private String message;
	
	@Value("#{'${mail.perfect.house.subscribe}'.split(',')}")
	private List<String> targets;

	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * 邮件推送不参与事务.
	 * </p>
	 * Propagation.NOT_SUPPORTED
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void push(PerfectHouseMailMessage msg) {
		executors.execute(() -> push0(msg));
	}

	void push0(PerfectHouseMailMessage msg) {
		SimpleMailMessage[] mailMessage = getMailMessage(msg);
		try {
			if (mailMessage != null && mailMessage.length > 0) {
				log.debug("邮件推送: {}", toString());
				mailSender.send(mailMessage);
			}
		} catch (Exception e) {
			// Read timed out 异常，邮件已发送，服务器响应超时不做throw处理
			if (!handlerTimeoutException(e)) {
				log.error("邮件推送: 推送异常", e);
			}
		}
	}
	
	/**
	 * Read timed out 异常，邮件已发送成功，但服务器响应超时不做throw处理.
	 * </p>
	 * => print warning
	 * 
	 * @param ex
	 */
	private boolean handlerTimeoutException(Exception ex) {
		if (ex instanceof MailSendException) {
			Map<Object, Exception> failedMessages = ((MailSendException) ex).getFailedMessages();
			if (failedMessages == null || failedMessages.isEmpty()) {
				return false;
			}

			for (Iterator<Exception> itr = failedMessages.values().iterator(); itr.hasNext();) {
				// Read timed out 异常，邮件已发送，服务器响应超时不做处理
				if (itr.next() instanceof SocketTimeoutException) {
					log.warn("邮件推送: Read timed out. 邮件已发送，服务器响应超时不做处理!");
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * 获取发送的SimpleMailMessage
	 * 
	 * @param msg
	 * @return
	 */
	private SimpleMailMessage[] getMailMessage(PerfectHouseMailMessage msg) {
		if (targets == null || targets.isEmpty()) {
			log.warn("邮件推送: 推送的用户为空 target ==> {}", targets);
			return null;
		}

		List<SimpleMailMessage> mailMessage = new ArrayList<>();
		for (String target : targets) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(target);
			message.setSentDate(new Date());
			message.setText(getMessage(msg.getBody()));
			message.setSubject(getSubject(msg.getSubject()));
			// add
			mailMessage.add(message);
		}

		return mailMessage.toArray(new SimpleMailMessage[] {});
	}
	
	private String getSubject(String arg) {
		return Objects.toString(subject, "").replace("{0}", arg);
	}
	
	private String getMessage(String[] body) {
		if (body == null || body.length <= 0) {
			return Utils.VOID_VALUE;
		}

		String replace = Utils.VOID_VALUE;
		for (int i = 0; i < body.length; i++) {
			replace = !StringUtils.isNoneBlank(replace) 
					? message.replace("{" + i + "}", body[i]) 
					: replace.replace("{" + i + "}", body[i]);
		}

		return replace;
	}
	
	@Override
	public String toString() {
		return "from=" + from 
				+ "\t to=" + Arrays.toString(targets.toArray()) 
				+ "\t subject=" + subject
				+ "\t message=" + message;
	}
}
