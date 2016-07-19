package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kit.Email;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface ProsessService {
    public void saveUnprocessedEmails();
    public void saveAllEmails();
}
