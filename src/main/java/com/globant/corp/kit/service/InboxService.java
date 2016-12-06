package com.globant.corp.kit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public boolean Send(String subject, String content);
    
}
