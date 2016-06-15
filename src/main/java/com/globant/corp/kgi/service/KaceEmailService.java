package com.globant.corp.kgi.service;

import com.globant.corp.kgi.model.beans.KaceEmail;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface KaceEmailService {
    public ArrayList<KaceEmail> getNewOnes();
    public ArrayList<KaceEmail> getAll();
    public KaceEmail getOne();
    public String sendApproval();
}