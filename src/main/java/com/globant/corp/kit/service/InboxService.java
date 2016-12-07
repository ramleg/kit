package com.globant.corp.kit.service;

import com.globant.corp.kit.exception.KaceMailingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.*;
/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public boolean Send(String subject, String content,String emailTo, Session session) throws KaceMailingException;
    public Session getSession();
}
