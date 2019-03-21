package com.sandu.mail;

import com.sandu.api.mail.service.FreeMailService;
import com.sandu.api.mail.service.TemplateMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DefaultsFreeMailService implements FreeMailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void push(List<String> targets, String subject, String body) {
        TemplateMailService.executors.execute(() -> push0(targets, subject, body));
    }

    private void push0(List<String> targets, String subject, String body) {
        List<SimpleMailMessage> mailMessage = new ArrayList<>();
        try {
            for (String target : targets) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(target);
                message.setSentDate(new Date());
                message.setText(body);
                message.setSubject(subject);
                // add
                mailMessage.add(message);
            }

            mailSender.send(mailMessage.toArray(new SimpleMailMessage[]{}));
        } catch (Exception e) {
            log.error("推送烘焙失败任务邮件异常, mailMessage => {}", targets, e);
        }
    }
}
