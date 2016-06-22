package com.globant.corp.kit.repository;
import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface EmailRepository extends CrudRepository<Email, Integer>, EmailRepositoryCustom{
    List<Email> findAll(Sort sort);
    List<Email> findByContent(String content);
    List<Email> findByProcessed(boolean processed, Sort sort);
    
}
