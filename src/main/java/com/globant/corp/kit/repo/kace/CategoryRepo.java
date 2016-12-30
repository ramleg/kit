package com.globant.corp.kit.repo.kace;

import com.globant.corp.kit.entity.kace.Category;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface CategoryRepo extends CrudRepository<Category, Integer>{
    
}
