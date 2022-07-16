package com.sxpcwlkj.utils;

import com.sxpcwlkj.entity.EmaiConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Created by 草帽boy on 2016/12/8.
 */
public class MailSendUtil {

    private final static String kimi = "品创网络";

    public static void sendTextMail(EmaiConfig info) throws Exception {

        Message message = getMessage(info);
        //消息发送的内容
        message.setText(info.getContent());

        Transport.send(message);
    }

    public static void sendHtmlMail(EmaiConfig info) throws Exception {

        Message message = getMessage(info);
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(info.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        message.setContent(mainPart);
        Transport.send(message);
    }


    private static Message getMessage(EmaiConfig info) throws Exception {
        final Properties p = System.getProperties();
        p.setProperty("mail.smtp.host", info.getSmtpServer());     // 发件人的邮箱的 SMTP 服务器地址
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.user", info.getSendEmail());
        p.setProperty("mail.smtp.pass", info.getSendPassword());

        p.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        p.setProperty("mail.smtp.port", info.getPort()+"");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.setProperty("mail.smtp.auth", "true"); // 需要请求认证
        p.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(p, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        p.getProperty("mail.smtp.user"),
                        p.getProperty("mail.smtp.pass"));
            }
        });
        // 开启debug模式，可以看到更多详细的输入日志
        session.setDebug(false);

        Message message = new MimeMessage(session);
        //消息发送的主题
        message.setSubject(info.getSubject());
        //接受消息的人
        message.setReplyTo(InternetAddress.parse(info.getReceptionEmail()));
        //消息的发送者
        message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), kimi));
        // 创建邮件的接收者地址，并设置到邮件消息中
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(info.getReceptionEmail()));
        // 消息发送的时间
        message.setSentDate(new Date());

        return message;
    }
}