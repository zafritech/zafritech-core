package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.LibraryItemTypes;

@Entity(name = "CORE_LIBRARY_ITEMS")
public class LibraryItem implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "folderId")
    private Folder folder;
    
    @Enumerated(EnumType.STRING)
    private LibraryItemTypes itemType;

    private String identifier;          // ISBN, Ref #, etc
    
    private String authors;
    
    private String publishers;
    
    private String itemTitle;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String itemPath;
    
    private String imagePath;
    
    private String format;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;
    
    private String version;
    
    private String keywords;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public LibraryItem() {
        
    }

    public LibraryItem(Folder folder, 
                       LibraryItemTypes itemType, 
                       String idendifier, 
                       String authors, 
                       String publishers, 
                       String itemTitle, 
                       String description, 
                       String itemPath, 
                       String imagePath, 
                       String format, 
                       Date publicationDate, 
                       String version, 
                       String keywords) {
        
        this.uuId = UUID.randomUUID().toString();
        this.folder = folder;
        this.itemType = itemType;
        this.identifier = idendifier;
        this.authors = authors;
        this.publishers = publishers;
        this.itemTitle = itemTitle;
        this.description = description;
        this.itemPath = itemPath;
        this.imagePath = imagePath;
        this.format = format;
        this.publicationDate = publicationDate;
        this.version = version;
        this.keywords = keywords;
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

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public LibraryItemTypes getItemType() {
        return itemType;
    }

    public void setItemType(LibraryItemTypes itemType) {
        this.itemType = itemType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemPath() {
        return itemPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

