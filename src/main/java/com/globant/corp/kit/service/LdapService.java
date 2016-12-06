package com.globant.corp.kit.service;

import com.globant.corp.kit.exception.LdapContextException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

/**
 *
 * @author ramiro.acoglanis
 */

public interface LdapService {
    
    public DirContext getContext() throws LdapContextException; 
    public boolean validateUser (String user,DirContext ctx);
}
