package com.globant.corp.kit.repository;
import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ramiro.acoglanis
 */
public interface EmailRepository extends CrudRepository<Email, Integer>, EmailRepositoryCustom{
    
    List<Email> findAll(Sort sort);
    List<Email> findByContent(String content);
    List<Email> findByProcessed(boolean processed);
    List<Email> findByProcessedOrderByUidDesc(boolean processed);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Email e set e.processed = true where e.processed = false")
    void markProcessedEmails();
}
