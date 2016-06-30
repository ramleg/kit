package com.globant.corp.kit.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 *
 * @author ramiro.acoglanis
 */
public interface ParserService {
    
    public HashMap<String,String> emailToTicket(String email) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
