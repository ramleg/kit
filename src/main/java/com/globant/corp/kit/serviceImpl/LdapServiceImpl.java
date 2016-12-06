package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
import com.globant.corp.kit.exception.LdapContextException;
import com.globant.corp.kit.service.LdapService;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */

@Service
public class LdapServiceImpl implements LdapService{
    
    @Autowired
    private AppConfig config;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public DirContext getContext() throws LdapContextException{
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, config.getLdapServer());
            env.put(Context.SECURITY_AUTHENTICATION,"simple");
            env.put(Context.SECURITY_PRINCIPAL, config.getLdapAdminUser());
            env.put(Context.SECURITY_CREDENTIALS,config.getLdapAdminPasswd());
            //Conseguimos contexto de conexion
            DirContext ctx = new InitialDirContext(env);
            return ctx;
        } catch (NamingException ex) {
            logger.error("ERROR: LDAP Service - Can't get 'DirContext'");
            throw new LdapContextException();
        }
    }
    
    @Override
    public boolean validateUser (String user,DirContext ctx){
        try{
            String search = "DC=globant,DC=com";
            SearchControls ctls = new SearchControls();
            ctls.setReturningObjFlag (true);
            //Asignamos los atributos a devolver
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            //Realiza la consulta, aplicando el filtro y trayendo los atributos especificados
            String filter = "(userPrincipalName=" + user + "@globant.com)";
            NamingEnumeration resultSet = ctx.search(search, filter, ctls);
            SearchResult result = (SearchResult) resultSet.next();
            return true;
        }catch(NamingException e){
            logger.info("INFO: Invalid User - '" + user + "'");
            return false;
        }
    }
    
    
    private void validateUserAndPass(String user,String passwd ) throws NamingException, LdapContextException{

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, config.getLdapServer());
        env.put(Context.SECURITY_AUTHENTICATION,"simple");
        env.put(Context.SECURITY_PRINCIPAL, getCN(user));
        env.put(Context.SECURITY_CREDENTIALS,passwd);
        //Conseguimos contexto de conexion
        new InitialDirContext(env);
    }
    
    private String getCN (String user) throws NamingException, LdapContextException{
        String search = "DC=globant,DC=com";
        SearchControls ctls = new SearchControls();
        ctls.setReturningObjFlag (true);
        //Asignamos los atributos a devolver
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        DirContext ctx = getContext();
        //Realiza la consulta, aplicando el filtro y trayendo los atributos especificados
        String filter = "(userPrincipalName=" + user + "@globant.com)";
        NamingEnumeration resultSet = ctx.search(search, filter, ctls);
        SearchResult result = (SearchResult) resultSet.next();
        close(ctx);
        return result.getNameInNamespace();
        
    }

    private ArrayList<String> getAtributes (String filter, String[] atr) throws LdapContextException { 
        try {
            ArrayList<String> data = null;
            String search = "DC=globant,DC=com";
            SearchControls ctls = new SearchControls();
            // Para que devuelva los elementos que buscamos
            ctls.setReturningObjFlag (true);
            //Asignamos los atributos a devolver
            ctls.setReturningAttributes(atr);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            
            DirContext ctx = getContext();
            //Realiza la consulta, aplicando el filtro y trayendo los atributos especificados
            NamingEnumeration resultSet = ctx.search(search, filter, ctls);
            
            while(resultSet.hasMore()){
                SearchResult result = (SearchResult) resultSet.next();
                for(String atributo : atr){
                    data.add(result.getAttributes().get(atributo).get().toString());
                    System.out.println(result.getAttributes().get(atributo).get().toString());
                }
            }
            close(ctx);
            return data;
        }catch (NamingException ex) {
            java.util.logging.Logger.getLogger(LdapServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
    public void close(DirContext ctx) {
        try {
            ctx.close();
        } catch (NamingException e) {
            // No se habia podido conectar, ya se habia cerrado la conexion, etc..
            e.printStackTrace();
        }
    }
    
    
}
