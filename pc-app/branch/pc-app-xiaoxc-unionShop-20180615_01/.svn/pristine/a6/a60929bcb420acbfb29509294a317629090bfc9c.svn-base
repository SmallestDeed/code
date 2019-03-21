package com.nork.common.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import com.nork.common.properties.AppProperties;


/**
 *
 * @author Qixuan.Chen
 */
public class SendEmail {

    static Logger logger = Logger.getLogger(SendEmail.class);

	public static final String HOST = "smtp.sanduspace.cn";
    //public static final String HOST = "smtp.126.com";
    public static final int PORT = 25;
    public static final String FROM = "app@sanduspace.cn";//发件人的email
    public static final String PWD = "1teqMmeIV9";//发件人密码
    // public static final String FROM = "sanduadmin@126.com";//发件人的email
    /*public static final String PWD = "1234qwer";//发件人密码
*/
    /**
     * 获取Session
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth" , true);
//        props.setProperty("mail.transport.protocol", "smtp");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }

    public static void send(String toEmail , String content) {
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
            msg.setContent(content , "text/html;charset=utf-8");
            msg.saveChanges();
            //Send the message
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
        catch (MessagingException mex) {
            logger.error(mex.getMessage());
            mex.printStackTrace();
        }
    }

    /**
     * 群发
     * @param toEmails
     * @param content
     */
    public static void massSend(String[] toEmails , String subject, String content) {
        if( toEmails == null ){
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
            for( String toEmail : toEmails ){
                address[count] = new InternetAddress(toEmail);
                count++;
            }
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");

            //Send the message
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            logger.error(mex.getMessage());
        }
    }

    /**
     * 发送邮件给渲染告警处理组
     * @param subject
     * @param content
     */
    public static void sendEmailForRenderWarning(String subject, String content) {
      //收件地址为渲染告警处理组
      String toEmail = Utils.getValueByFileKey(AppProperties.APP, AppProperties.RENDER_WARNING_EMAIL, "renderwarning@sanduspace.cn");
      if( toEmail == null ){
          return;
      }
      Session session = getSession();
      try {
          // Instantiate a message
          Message msg = new MimeMessage(session);

          //Set message attributes
          msg.setFrom(new InternetAddress(FROM));
          InternetAddress[] address = {new InternetAddress(toEmail)};
          msg.setRecipients(Message.RecipientType.TO, address);
          msg.setSubject(subject);
          msg.setSentDate(new Date());
          msg.setContent(content , "text/html;charset=utf-8");

          //Send the message
          Transport transport = session.getTransport("smtp");
          transport.connect();
          transport.sendMessage(msg, msg.getAllRecipients());
          transport.close();
          logger.info("--------sendEmail to " + toEmail + "success---------");
      }
      catch (MessagingException mex) {
          mex.printStackTrace();
          logger.error(mex.getMessage());
      }
    }

    public static void main(String[] args) {
        /*SendEmail.send("", "你好吗？");*/
        /*SendEmail.massSend(new String[]{""},"","");*/
    }



}

