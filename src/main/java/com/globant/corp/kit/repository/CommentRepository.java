package com.globant.corp.kit.repository;

import com.globant.corp.kit.model.beans.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface CommentRepository extends CrudRepository<Comment, Integer>{
    
}
