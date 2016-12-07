package com.globant.corp.kit.repo.kace;

import com.globant.corp.kit.entity.kace.Queue;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */

public interface QueueRepo extends CrudRepository<Queue, Integer>{
    public List<Queue> findByAllowed(Integer id);
    public Queue findById(Integer id);
}
