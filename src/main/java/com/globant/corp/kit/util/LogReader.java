package com.globant.corp.kit.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ramiro.acoglanis
 */
public class LogReader {
    
    public String getLog(String param){
        FileInputStream fstream;
        String filePath = getLogPath(param);
        try{
            fstream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            String htmlLog = "";
            /* read log line by line */
            while ((strLine = br.readLine()) != null)   {
              /* parse strLine to obtain what you want */
              if(strLine.contains("ERROR")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"red\">" + strLine + "</font></br>";
              }else if(strLine.contains("***")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"green\">" + strLine + "</font></br>";
              }else if(strLine.contains("<--")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"blue\">" + strLine + "</font></br>";
              }else{
                  htmlLog = htmlLog + "<font size=\"3\" color=\"black\">" + strLine + "</font></br>";
              }
            }
            in.close();
            return htmlLog;
        } catch (Exception e) {
            return "<font size=\"8\" color=\"red\">ERROR: Log not found [" + filePath + "]</font></br>" + 
                    "<font size=\"3\" color=\"black\">Tip: the parameter format is 'yyyy-mm-dd-index'</font></br>";
        }
    }
    
    private String getLogPath(String param){
        
        LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
        String path = "";
        
        if(param.equals("")){
            path = context.getProperty("LOG_ROOT")+context.getProperty("FILENAME")+".log";
        }else{
            path = context.getProperty("LOG_ROOT")+context.getProperty("ARCHIVED")+context.getProperty("FILENAME")+"." + param + ".log";
        }
        
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            String winPath = "C:" + path.replace("/", "\\");
            return winPath;
        }else{
            return path;
        }
        
        
    }
    
}