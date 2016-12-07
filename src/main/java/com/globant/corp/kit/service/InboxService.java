package com.globant.corp.kit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.*;
/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public boolean Send(String subject, String content, Session session);
    public Session getSession();
}
