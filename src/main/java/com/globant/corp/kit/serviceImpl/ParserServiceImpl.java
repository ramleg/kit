package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.model.beans.Ticket;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ParserServiceImpl {
    
    @Value("${kit.emailparser.delimiter}")
    private String delimiter;
    
    public Ticket emailToTicket(Email email){
        
        Ticket ticket = new Ticket();
        ArrayList<String> fields = getFields(Ticket.class);
        String[] splitedSubject = email.getSubject().split(delimiter);
        String[] splitedContent = email.getContent().split(delimiter);
        
        String lastFieldRead = "";
        HashMap<String,String> parsedMap = new HashMap<>();
        
        for(String s:splitedContent){
            if(fields.contains(s)){
                lastFieldRead=s;
            }else{
                
            }
        }
        return null;
    }
    
    private <T> ArrayList<String> getFields(Class<T> c){
        Field[] array = c.getDeclaredFields();
        ArrayList<String> list = new ArrayList<>();
        for(Field f: array)
            list.add(f.getName());
        return list;
    }
}
