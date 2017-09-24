package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_LIBRARY_CATEGORIES")
public class LibraryCategory implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    private String categoryName;
    
    private String categoryPath;
    
    @ManyToOne
    @JoinColumn(name = "parentId")
    private LibraryCategory parent;
    
    private Integer sortIndex;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public LibraryCategory() {
        
    }

    public LibraryCategory(String categoryName, LibraryCategory parent) {
        
        this.uuId = UUID.randomUUID().toString();
        this.categoryName = categoryName;
        this.parent = parent;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public LibraryCategory(String categoryName, String categoryPath, LibraryCategory parent) {
        
        this.uuId = UUID.randomUUID().toString();
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.parent = parent;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public LibraryCategory(String categoryName, String categoryPath, LibraryCategory parent, Integer sortIndex) {
        
        this.uuId = UUID.randomUUID().toString();
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.parent = parent;
        this.sortIndex = sortIndex;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "LibraryCategory{" + "id=" + getId() + ", uuId=" + getUuId() + ", categoryName=" 
                + getCategoryName() + ", categoryPath=" + getCategoryPath() + ", parent=" 
                + getParent() + ", sortIndex=" + getSortIndex() + ", creationDate=" + getCreationDate() + '}';
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public LibraryCategory getParent() {
        return parent;
    }

    public void setParent(LibraryCategory parent) {
        this.parent = parent;
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
}

