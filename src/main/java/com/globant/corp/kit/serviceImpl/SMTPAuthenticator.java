package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.EmailConfiguration;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author ramiro.acoglanis
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {

    private EmailConfiguration config;
    
    public SMTPAuthenticator(EmailConfiguration config) {
        this.config = config;
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getEmailAccount(), config.getEmailPasswd());
    }
}