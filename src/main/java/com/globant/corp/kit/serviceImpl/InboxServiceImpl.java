package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.util.SMTPAuthenticator;
import com.globant.corp.kit.configuration.EmailConfig;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.InboxService;
import java.util.Date;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class InboxServiceImpl implements InboxService{

    @Autowired
    EmailConfig config;
    
    private Folder folder;
    private UIDFolder ufolder;
    private Store store;

    
    @Override
    public boolean Send(String subject, String content, Session session) {

        try {
            
            MimeMessage msg = new MimeMessage(session);
            Multipart multiPart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.addHeader("Delivered-To", config.getEmailAccount());
            InternetAddress mailFrom = new InternetAddress(config.getEmailAccount());
            msg.setFrom(mailFrom);
            
            InternetAddress mailTo = new InternetAddress(config.getEmailTo());
            msg.addRecipient(Message.RecipientType.TO,mailTo);

            htmlPart.setContent("messege from java", "text/html; charset=utf-8");
            multiPart.addBodyPart(htmlPart);
            textPart.setContent("messege from java", "text/plain; charset=utf-8");
            multiPart.addBodyPart(textPart);
            
            msg.setContent(multiPart);
            msg.setContent("<div>" + content + "</div>", "text/html; charset=utf-8");

            msg.saveChanges();
            
            Transport.send(msg);
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(InboxServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    @Override
    public Session getSession() {
        Properties props = new Properties();
        
        props.put("mail.smtp.user", config.getEmailAccount());
        props.put("mail.smtp.host", config.getSendHost());
        props.put("mail.smtp.port", config.getPort());
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", config.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        Authenticator auth = new SMTPAuthenticator(config);
        Session session = Session.getInstance(props, auth);
        
        return session;
    }

}
