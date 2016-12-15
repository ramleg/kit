package com.globant.corp.kit.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;

/**
 *
 * @author ramiro.acoglanis
 */
public class LogReader {
    
    public String getLog(){
        
        try{
            FileInputStream fstream;
            if(System.getProperty("os.name").toLowerCase().contains("win")){
                fstream = new FileInputStream("C:\\home\\sysadmin\\kgilogs\\audit.log");
            }else{
                fstream = new FileInputStream("/home/sysadmin/kgilogs/audit.log");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            String htmlLog = "";
            /* read log line by line */
            while ((strLine = br.readLine()) != null)   {
              /* parse strLine to obtain what you want */
              if(strLine.contains("ERROR")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"red\">" + strLine + "</font></br>";
              }else if(strLine.contains("-->>")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"green\">" + strLine + "</font></br>";
              }else if(strLine.contains("**")){
                  htmlLog = htmlLog + "<font size=\"3\" color=\"blue\">" + strLine + "</font></br>";
              }else{
                  htmlLog = htmlLog + "<font size=\"3\" color=\"black\">" + strLine + "</font></br>";
              }
              
            }
            in.close();
            return htmlLog;
        } catch (Exception e) {
            return "<font size=\"10\" color=\"red\">ERROR: Log not found</font></br>";
        }
        
        
    }
    
}
