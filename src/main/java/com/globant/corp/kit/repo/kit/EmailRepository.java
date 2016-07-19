package com.globant.corp.kit.repo.kit;
import com.globant.corp.kit.entity.kit.Email;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface EmailRepository extends CrudRepository<Email, Integer>{
    
    List<Email> findAll(Sort sort);
    List<Email> findByContent(String content);
    List<Email> findByProcessed(boolean processed);
    List<Email> findByProcessedOrderByUidDesc(boolean processed);
}
