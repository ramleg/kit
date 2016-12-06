package com.globant.corp.kit.service;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ramiro.acoglanis
 */
public interface RestServiceConsumer {
    public ResponseEntity postToGata(HashMap<String, String> body);
}
