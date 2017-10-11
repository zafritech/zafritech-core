package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

@Entity(name = "CORE_PROJECT_WBS_PACKAGES")
public class ProjectWbsPackage implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    private String wbsNumber;
    
    private String wbsCode;
    
    private String wbsName;
    
    @ManyToOne
    @JoinColumn(name = "parentId")
    private ProjectWbsPackage parent;
    
    @ManyToOne
    @JoinColumn(name = "leaderId")
    private User categoryLeader;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "CORE_PROJECT_WBS_MEMBERS",
               joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> categoryMembers = new ArrayList<>();
    
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ProjectWbsPackage() {
        
    }

    public ProjectWbsPackage(Project project, String wbsNumber, String wbsCode, String wbsName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.wbsNumber = wbsNumber;
        this.wbsCode = wbsCode;
        this.wbsName = wbsName;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ProjectWbsPackage(Project project, String wbsNumber, String wbsCode, String wbsName, ProjectWbsPackage parent) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.wbsNumber = wbsNumber;
        this.wbsCode = wbsCode;
        this.wbsName = wbsName;
        this.parent = parent;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "ProjectWbsPackage{" + "id=" + getId() + ", uuId=" + getUuId() + ", project=" 
                + getProject() + ", wbsNumber=" + getWbsNumber() + ", wbsCode=" + getWbsCode() 
                + ", wbsName=" + getWbsName() + ", parent=" + getParent() + ", categoryLeader=" 
                + getCategoryLeader() + ", categoryMembers=" + getCategoryMembers() 
                + ", creationDate=" + getCreationDate() + '}';
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

    public String getWbsNumber() {
        return wbsNumber;
    }

    public void setWbsNumber(String wbsNumber) {
        this.wbsNumber = wbsNumber;
    }

    public String getWbsCode() {
        return wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public ProjectWbsPackage getParent() {
        return parent;
    }

    public void setParent(ProjectWbsPackage parent) {
        this.parent = parent;
    }

    public User getCategoryLeader() {
        return categoryLeader;
    }

    public void setCategoryLeader(User categoryLeader) {
        this.categoryLeader = categoryLeader;
    }

    public List<User> getCategoryMembers() {
        return categoryMembers;
    }

    public void setCategoryMembers(List<User> categoryMembers) {
        this.categoryMembers = categoryMembers;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}

