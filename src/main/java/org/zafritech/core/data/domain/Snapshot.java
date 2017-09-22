package org.zafritech.core.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.SnapshotType;

@Entity(name = "CORE_SNAPSHOTS")
public class Snapshot implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @Enumerated(EnumType.STRING)
    private SnapshotType snapshotType;
 
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "CORE_SNAPSHOT_VALUE_SETS",
               joinColumns = {@JoinColumn(name = "snapshot_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "snapshot_value_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private List<SnapshotValue> snapshotValues = new ArrayList<SnapshotValue>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date snapshotDate;

    public Snapshot() {
        
        this.uuId = UUID.randomUUID().toString();
        this.snapshotDate = new Timestamp(System.currentTimeMillis());
    }

    public Snapshot(Project project, SnapshotType snapshotType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.snapshotType = snapshotType;
        this.snapshotDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "New data snapshot created: " + "Snapshot{" + "id=" + getId() + ", uuId=" + getUuId() + ", project=" + 
                getProject() + ", snapshotType=" + getSnapshotType() + ", snapshotValues=" + 
                getSnapshotValues() + ", snapshotDate=" + getSnapshotDate() + '}';
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SnapshotType getSnapshotType() {
        return snapshotType;
    }

    public void setSnapshotType(SnapshotType snapshotType) {
        this.snapshotType = snapshotType;
    }

    public List<SnapshotValue> getSnapshotValues() {
        return snapshotValues;
    }

    public void setSnapshotValues(List<SnapshotValue> snapshotValues) {
        this.snapshotValues = snapshotValues;
    }

    public Date getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(Date snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}

