package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_FOLDERS")
public class Folder implements Serializable {

    private static final long serialVersionUID = -7335525087566303043L;

    @Id
    @GeneratedValue
    private Long Id;

    private String uuId;

    @Column(nullable = false)
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "folderTypeId")
    private EntityType folderType;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Folder parent;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    private Integer sortIndex;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Override
    public String toString() {
        return "Folder {"
                + "ID: " + getId()
                + ", Folder Name = '" + getFolderName() + '\''
                + ", Folder Type = '" + getFolderType() + '\''
                + '}';
    }

    public Folder() {
    }

    public Folder(String folderName, EntityType folderType, Project project) {

        this.uuId = UUID.randomUUID().toString();
        this.folderName = folderName;
        this.folderType = folderType;
        this.parent = null;
        this.project = project;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Folder(String folderName, EntityType folderType, Folder parent, Project project) {

        this.uuId = UUID.randomUUID().toString();
        this.folderName = folderName;
        this.folderType = folderType;
        this.parent = parent;
        this.project = project;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Folder(String folderName, EntityType folderType, Folder parent, Project project, Integer sortIndex) {

        this.uuId = UUID.randomUUID().toString();
        this.folderName = folderName;
        this.folderType = folderType;
        this.parent = parent;
        this.project = project;
        this.sortIndex = sortIndex;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return Id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public EntityType getFolderType() {
        return folderType;
    }

    public void setFolderType(EntityType folderType) {
        this.folderType = folderType;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

