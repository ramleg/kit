package com.globant.corp.kit.service;

/**
 *
 * @author ramiro.acoglanis
 */
public interface RestConsumerService {
    public String post(String url, String data);
    public String get(String url, String data);
}
