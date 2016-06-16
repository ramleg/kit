package com.globant.corp.kit.exception;

/**
 *
 * @author ramiro.acoglanis
 */
public class EmailException extends Exception{

    public EmailException(String message) {
        super(message);
    }
    @Override
    public String toString(){
        return "KaceEmail Error: " + super.getMessage();
    }
}
