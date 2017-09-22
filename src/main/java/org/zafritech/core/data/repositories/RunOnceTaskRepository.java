/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.RunOnceTask;

/**
 *
 * @author LukeS
 */
public interface RunOnceTaskRepository extends CrudRepository<RunOnceTask, Long> {
    
    RunOnceTask findByTaskName(String taskName);
    
    RunOnceTask findByTaskNameAndCompleted(String taskName, boolean completed);
}
