package org.codepay.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * Descriptions 发送邮件工具类 博客:http://www.cnblogs.com/jingmoxukong/p/5223168.html
 *
 * @author tian.jinsong
 * @date 2015年8月21日
 */
public class EmailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    private static String SMTP_HOST;
    private static String SMTP_USER;
    private static String SMTP_PWD;
    private static boolean SMTP_AUTH;
    private static String NICK_NAME;

    /**
     * 发送邮件
     *
     * @param subject 主题
     * @param text    内容
     * @param from    发件人
     * @param to      收件人
     * @return
     */
    public static boolean send(String subject, String text, String from, String to) {
        Session session = getSession();
        Message message = new MimeMessage(session);
        try {
            message.setSubject(subject);
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(text, "text/html;charset=utf-8");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            if (send(session, message, from)) {
                LOGGER.info("发送邮件成功,主题={},内容={},发件人={},收件人={}", subject, text, from, to);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("发送邮件失败:{}", ExceptionUtil.getException(e));
        }
        return false;
    }

    /**
     * 发送带附件邮件
     *
     * @param subject  主题
     * @param text     内容
     * @param from     发件人
     * @param to       收件人
     * @param file     文件
     * @param fileName 文件名
     * @return
     */
    public static boolean send(String subject, String text, String from, String to, File file, String fileName) {
        try {
            Session session = getSession();
            Message message = new MimeMessage(session);
            Multipart mm = new MimeMultipart();

            BodyPart mbp = new MimeBodyPart();
            // 邮件内容,也可以使纯文本"text/plain"
            mbp.setContent(text, "text/html;charset=utf-8");
            mm.addBodyPart(mbp);
            if (null != file) {
                FileDataSource fds = new FileDataSource(file);
                DataHandler dh = new DataHandler(fds);
                // MimeUtility.encodeWord可以避免文件名乱码
                mbp.setFileName(MimeUtility.encodeWord(fileName));
                mbp.setDataHandler(dh);
            }

            message.setSubject(subject);
            message.setContent(mm);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            if (send(session, message, from)) {
                LOGGER.info("发送邮件成功,主题={},内容={},发件人={},收件人={},文件名={}", subject, text, from, to, fileName);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("发送邮件失败:{}", ExceptionUtil.getException(e));
        }
        return false;
    }

    /**
     * 发送带附件邮件
     *
     * @param subject  主题
     * @param text     内容
     * @param from     发件人
     * @param tos      多个收件人
     * @param ccs      多个抄送人
     * @param file     文件
     * @param fileName 文件名
     * @return
     */
    public static boolean send(String subject, String text, String from, String[] tos, String[] ccs, File file,
                               String fileName) {
        try {
            Session session = getSession();
            Message message = new MimeMessage(session);

            Multipart mm = new MimeMultipart();
            BodyPart mbp = new MimeBodyPart();
            // 邮件内容,也可以使纯文本"text/plain"
            mbp.setContent(text, "text/html;charset=utf-8");
            mm.addBodyPart(mbp);
            if (null != file) {
                FileDataSource fds = new FileDataSource(file);
                DataHandler dh = new DataHandler(fds);
                // MimeUtility.encodeWord可以避免文件名乱码
                mbp.setFileName(MimeUtility.encodeWord(fileName));
                mbp.setDataHandler(dh);
            }
            message.setSubject(subject);
            message.setContent(mm);

            // 设置发件人
            if (tos != null) {
                Address[] toAddresses = new InternetAddress[tos.length];
                for (int i = 0; i < tos.length; i++) {
                    toAddresses[i] = new InternetAddress(tos[i]);
                }
                message.setRecipients(Message.RecipientType.TO, toAddresses);
            }

            // 设置抄送
            if (ccs != null) {
                Address[] ccAddresses = new InternetAddress[ccs.length];
                for (int i = 0; i < ccs.length; i++) {
                    ccAddresses[i] = new InternetAddress(ccs[i]);
                }
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }

            if (send(session, message, from)) {
                LOGGER.info("发送邮件成功,主题={},内容={},发件人={},收件人={},抄送人={},文件名={}", subject, text, from,
                        Arrays.toString(tos), Arrays.toString(ccs), fileName);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("发送邮件失败:{}", ExceptionUtil.getException(e));
        }
        return false;
    }

    /**
     * 发送带抄送人邮件
     *
     * @param subject 主题
     * @param text    内容
     * @param from    发件人
     * @param tos     多个收件人
     * @param ccs     多个抄送人
     * @return
     */
    public static boolean send(String subject, String text, String from, String[] tos, String[] ccs) {
        try {
            Session session = getSession();
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(text, "text/html;charset=utf-8");
            // 设置发件人
            if (tos != null) {
                Address[] toAddresses = new InternetAddress[tos.length];
                for (int i = 0; i < tos.length; i++) {
                    toAddresses[i] = new InternetAddress(tos[i]);
                }
                message.setRecipients(Message.RecipientType.TO, toAddresses);
            }

            // 设置抄送
            if (ccs != null) {
                Address[] ccAddresses = new InternetAddress[ccs.length];
                for (int i = 0; i < ccs.length; i++) {
                    ccAddresses[i] = new InternetAddress(ccs[i]);
                }
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }

            if (send(session, message, from)) {
                LOGGER.info("发送邮件成功,主题={},内容={},发件人={},收件人={},抄送人={}", subject, text, from, Arrays.toString(tos),
                        Arrays.toString(ccs));
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("发送邮件失败:{}", ExceptionUtil.getException(e));
        }
        return false;
    }

    /**
     * 发送邮件.
     *
     * @param session
     * @param message
     * @param from
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static boolean send(Session session, Message message, String from) throws MessagingException,
            UnsupportedEncodingException {
        Transport transport = null;
        boolean success = false;
        try {
            if (null == from || from.isEmpty()) {
                message.setFrom(new InternetAddress(MimeUtility.encodeWord(NICK_NAME) + "<" + SMTP_USER + ">"));
            } else {
                message.setFrom(new InternetAddress(from));
            }
            message.setSentDate(new Date());
            // 保存邮件
            // message.saveChanges();
            transport = session.getTransport();
            if (SMTP_AUTH) {// 是否需要验证才能发邮件
                transport.connect(SMTP_HOST, 25, SMTP_USER, SMTP_PWD);
            } else {
                transport.connect();
            }

            if (transport.isConnected()) {
                transport.sendMessage(message, message.getAllRecipients());
                transport.isConnected();
                success = true;
            }
        } finally {
            if (null != transport && transport.isConnected()) {
                transport.isConnected();
            }
        }
        return success;
    }

    private static Session getSession() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");// 什么方式连接
        props.setProperty("mail.smtp.auth", String.valueOf(SMTP_AUTH));// smtp是否验证
        props.setProperty("mail.smtp.host", SMTP_HOST); // 邮件服务器
        Session session = Session.getInstance(props);
        return session;
    }

    /**
     * 设置SMTP服务器地址，如smtp.263.net
     *
     * @param host
     */
    public static void setSmtpHost(String host) {
        SMTP_HOST = host;
    }

    /**
     * 设置SMTP默认的登陆用户名
     *
     * @param user
     */
    public static void setSmtpUser(String user) {
        SMTP_USER = user;
    }

    /**
     * 设置SMTP默认的登陆密码
     *
     * @param pwd
     */
    public static void setSmtpPwd(String pwd) {
        SMTP_PWD = pwd;
    }

    /**
     * 设置SMTP服务器是否需要用户认证，默认为true
     *
     * @param auth
     */
    public static void setSmtpAuth(boolean auth) {
        SMTP_AUTH = auth;
    }

    /**
     * 设置发件人昵称.
     *
     * @param nickname
     */
    public static void setNickname(String nickname) {
        NICK_NAME = nickname;
    }

    public static void main(String[] args) {
        String subject = "测试", txt = "test", from = "", to = "wangliping@zhangyue.com";
        send(subject, txt, from, to);
    }
}
