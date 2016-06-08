package com.globant.corp.kgi.repository;

import com.globant.corp.kgi.model.beans.KaceEmail;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface KaceEmailRepo {
    
    public ArrayList<KaceEmail> getFromCount(String folder, int count);
    public ArrayList<KaceEmail> getAll(String folder);
    public KaceEmail getOne(String folder);
    
}
