package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kit.Email;
import com.globant.corp.kit.entity.kit.Ticket;
import com.globant.corp.kit.service.ParserService;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ParserServiceImpl  implements ParserService{
    
    @Value("${kit.emailparser.delimiter}")
    private String delimiter;
    
    private Ticket ticket;
    private ArrayList<String> fields;
    
    @Override
    public Ticket emailToTicket(Email email) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        ticket = new Ticket();
        fields = getFields(Ticket.class);
        
        String[] splitedSubject = email.getSubject().split(delimiter);
        String[] splitedContent = email.getContent().split(delimiter);
//        String subject = "231654" + delimiter + "new";// "new" // "modify" // "comment"
//        String[] splitedSubject = subject.split(delimiter);
//        String[] splitedContent = email.split(delimiter);
        
        String lastFieldRead = "";
        HashMap<String,String> parsedContent = new HashMap<>();
        
        // Obtain values from subject
        if(splitedSubject.length == 2){
            parsedContent.put("num", splitedSubject[0]);
        }
        
        for(String sc: splitedContent){
            if(fields.contains(sc)){
                    lastFieldRead = sc;
                    parsedContent.put(sc, "");
            }else{
                String value = parsedContent.get(lastFieldRead);
                if(value.equals("")){
                    parsedContent.put(lastFieldRead,sc);
                }else{
                    parsedContent.put(lastFieldRead,value + delimiter + sc);
                }
            }
        }
            
//      Method method = ticket.getClass().getMethod("set" + WordUtils.capitalize(sc) , String.class);
//      method.invoke(ticket, sc2);
        return null;
    }
    
    private <T> ArrayList<String> getFields(Class<T> c){
        Field[] array = c.getDeclaredFields();
        ArrayList<String> list = new ArrayList<>();
        for(Field f: array)
            list.add(f.getName());
        return list;
    }
    
    private <T> ArrayList<String> getMethods(Class<T> c){
        Method[] array = c.getClass().getMethods();
        ArrayList<String> list = new ArrayList<>();
        for(Method m: array)
            list.add(m.getName());
        return list;
    }

    @Override
    public List<Email> normalizeEmail(List<Email> emails) {
        if(!emails.isEmpty()){
            List<Email> returnList = new ArrayList<>();
            for(Email e: emails){
                String action = e.getSubject().split(delimiter)[1];
                e.setAction(action);
                returnList.add(e);
            }
            return returnList;
        }else{
            return null;
        }
    }

}
