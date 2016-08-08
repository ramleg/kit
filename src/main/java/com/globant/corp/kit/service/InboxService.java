package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.local.Email;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public ArrayList<Email> getAll();
    public Email getLast();
    public List<Email> getUnread(long lastRead);
    public String Send(String num, String approver, String comment);
    
}
