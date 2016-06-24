package com.globant.corp.kit.service;

import com.globant.corp.kit.model.beans.Email;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface ProsessService {
    public void saveUnprocessedEmails();
    public void saveAllEmails();
}
