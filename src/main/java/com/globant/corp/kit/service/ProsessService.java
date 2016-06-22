package com.globant.corp.kit.service;

import com.globant.corp.kit.model.beans.Email;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface ProsessService {
    public List<Email> saveUnprocessedEmails();
    public void processAll();
}
