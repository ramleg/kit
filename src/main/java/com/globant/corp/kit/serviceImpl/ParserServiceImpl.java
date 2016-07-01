package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.model.beans.Ticket;
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
    
    @Override
    public HashMap<String,String> emailToTicket(String email) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        Ticket ticket = new Ticket();
        
        ArrayList<String> fields = getFields(Ticket.class);
        
//        String[] splitedSubject = email.getSubject().split(delimiter);
//        String[] splitedContent = email.getContent().split(delimiter);
        String[] splitedContent = email.split(delimiter);
        
        String lastFieldRead = "";
        HashMap<String,String> parsedContent = new HashMap<>();
        int i = 0;
        int len = splitedContent.length;
        
        while(i < len){
            
            String sc = splitedContent[i];
            
            if(fields.contains(sc)){
                    lastFieldRead = sc;
                    parsedContent.put(sc, "");
                    
//                Method method = ticket.getClass().getMethod("set" + WordUtils.capitalize(sc) , String.class);
//                method.invoke(ticket, sc2);
            }else{
                
                String value = parsedContent.get(lastFieldRead);
                if(value.equals("")){
                    parsedContent.put(lastFieldRead,sc);
                }else{
                    parsedContent.put(lastFieldRead,value + delimiter + sc);
                }
                
            }
            
            i++;
        }
        
        
        
            
        return parsedContent;
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
}
