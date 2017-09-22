package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_RUNONCE_TASKS")
public class RunOnceTask implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String taskName;
    
    private boolean completed;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedDate;

    public RunOnceTask() {
        
    }

    public RunOnceTask(String taskName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.taskName = taskName;
        this.completed = false;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }
}

