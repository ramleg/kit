package com.globant.corp.kit.serviceImpl;

import javax.mail.PasswordAuthentication;

/**
 *
 * @author ramiro.acoglanis
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication(String emailAccount, String emailPasswd) {
        return new PasswordAuthentication(emailAccount, emailPasswd);
    }
}