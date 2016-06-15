package com.globant.corp.kgi.exception;

/**
 *
 * @author ramiro.acoglanis
 */
public class KaceEmailException extends Exception{

    public KaceEmailException(String message) {
        super(message);
    }
    @Override
    public String toString(){
        return "KaceEmail Error: " + super.getMessage();
    }
}
