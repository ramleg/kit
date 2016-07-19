package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kit.Email;
import com.globant.corp.kit.entity.kit.Ticket;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface ParserService {
    
    public List<Email> normalizeEmail(List<Email> emails);
    public Ticket emailToTicket(Email email) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
