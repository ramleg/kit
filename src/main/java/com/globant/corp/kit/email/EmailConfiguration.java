package com.globant.corp.kit.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramiro.acoglanis
 */
@Component
public class EmailConfiguration {
    
    @Value("${kit.email.settings.protocol}")
    private String protocol;
    @Value("${kit.email.settings.read-host}")
    private String readHost;
    @Value("${kit.email.settings.send-host}")
    private String sendHost;
    @Value("${kit.email.settings.port}")
    private String port;
    @Value("${kit.email.settings.emailaccount}")
    private String emailAccount;
    @Value("${kit.email.settings.emailpasswd}")
    private String emailPasswd;
    @Value("${kit.email.settings.emailto}")
    private String emailTo;
    @Value("${kit.email.settings.read-folder}")
    private String readFolder;

    public String getProtocol() {
        return protocol;
    }

    public String getReadHost() {
        return readHost;
    }

    public String getSendHost() {
        return sendHost;
    }

    public String getPort() {
        return port;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public String getEmailPasswd() {
        return emailPasswd;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public String getReadFolder() {
        return readFolder;
    }
    
}
