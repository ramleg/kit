package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.local.Email;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface EmailService {
    
    public Email getOne(int id);
    public List<Email> getByContent(String content);
    public Iterable<Email> getAll();
    public void saveOne(Email kaceEmail);
    public void saveUnread(List<Email> kaceEmails);
}