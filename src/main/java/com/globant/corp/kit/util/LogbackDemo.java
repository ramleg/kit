package com.globant.corp.kit.util;

/**
 *
 * @author ramiro.acoglanis
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class LogbackDemo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void performTask(){
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.warn("This is a WARNING message.");
        logger.error("This is an ERROR message.");
    }
}