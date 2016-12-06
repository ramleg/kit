package com.globant.corp.kit.util;

import com.globant.corp.kit.configuration.EmailConfig;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author ramiro.acoglanis
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {

    private EmailConfig config;
    
    public SMTPAuthenticator(EmailConfig config) {
        this.config = config;
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getEmailAccount(), config.getEmailPasswd());
    }
}