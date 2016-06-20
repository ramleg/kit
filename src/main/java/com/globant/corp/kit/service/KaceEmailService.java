package com.globant.corp.kit.service;

import com.globant.corp.kit.model.beans.KaceEmail;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface KaceEmailService {
    
    public KaceEmail getOne(int id);
    public Iterable<KaceEmail> getAll();
    public void saveOne(KaceEmail kaceEmail);
    public void saveUnread(Iterable<KaceEmail> kaceEmails);
}