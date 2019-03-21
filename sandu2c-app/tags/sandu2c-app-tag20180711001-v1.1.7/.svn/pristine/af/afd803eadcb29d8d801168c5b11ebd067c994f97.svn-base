package com.sandu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


/**
 * @author Qixuan.Chen
 */
public class SendEmail {

    //	public static final String HOST = "mail.sanduspace.com";
    public static final String HOST = "smtp.126.com";
    public static final int PORT = 25;
    //    public static final String FROM = "sanduadmin@sanduspace.com";//发件人的email
    public static final String FROM = "sanduadmin@126.com";//发件人的email
    public static final String PWD = "1234qwer";//发件人密码
    static Logger logger = LoggerFactory.getLogger(SendEmail.class);

    /**
     * 获取Session
     *
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth", true);
//        props.setProperty("mail.transport.protocol", "smtp");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getDefaultInstance(props, authenticator);

        return session;
    }

    public static void send(String toEmail, String content) {
        Session session = getSession();
        try {
            //////System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("账号激活邮件");
            msg.setSentDate(new Date());
            msg.setContent(content, "text/html;charset=utf-8");
            msg.saveChanges();
            Transport.send(msg);
            //Send the message
            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException mex) {
            logger.error(mex.getMessage());
            mex.printStackTrace();
        }
    }

    /**
     * 群发
     *
     * @param toEmails
     * @param content
     */
    public static void massSend(String[] toEmails, String subject, String content) {
        if (toEmails == null) {
            return;
        }
        Session session = getSession();
        try {
            //////System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = new InternetAddress[toEmails.length];
            int count = 0;
            for (String toEmail : toEmails) {
                address[count] = new InternetAddress(toEmail);
                count++;
            }
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(content, "text/html;charset=utf-8");

            //Send the message
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException mex) {
            mex.printStackTrace();
            logger.error(mex.getMessage());
        }
    }

    public static void main(String[] args) {
        SendEmail.massSend(new String[]{"296278505@qq.com", "1358328486@qq.com", "786231564@qq.com", "36626562@qq.com", "124302412@qq.com"}, "账号激活邮件", "激活码为：361584");
    }
}

