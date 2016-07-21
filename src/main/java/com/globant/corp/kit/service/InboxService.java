package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.local.Email;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public List<Email> getAll();
    public Email getLast();
    public List<Email> getUnread(long lastRead);
    
    
}
