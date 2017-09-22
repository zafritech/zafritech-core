package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.DocumentStatus;

@Entity(name = "CORE_DOCUMENTS")
public class Document implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String identifier;

    private String documentName;

    private String documentLongName;

    @Column(columnDefinition = "TEXT")
    private String documentDescription;

    @ManyToOne
    @JoinColumn(name = "documentTypeId")
    private DocumentType documentType;
    
    @ManyToOne
    @JoinColumn(name = "infoClassId")
    private InformationClass infoClass;
    
    private String documentIssue;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "folderId")
    private Folder folder;
    
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;
    
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CORE_DOCUMENT_DEFINITIONS",
               joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "definition_id", referencedColumnName = "id")}
    )
    @OrderBy("term")
    private List<Definition> definitions = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CORE_DOCUMENT_DISTRIBUTIONS",
               joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    @OrderBy("firstName")
    private List<User> distributions =  new ArrayList<>();

    public Document() {
        
    }

    public Document(String identifier, 
                    String documentName, 
                    DocumentType documentType, 
                    Project project, 
                    Folder folder,
                    InformationClass infoClass,
                    String issue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.identifier = identifier;
        this.documentName = documentName;
        this.documentType = documentType;
        this.project = project;
        this.folder = folder;
        this.status = DocumentStatus.DRAFT;
        this.infoClass = infoClass;
        this.documentIssue = issue;
        this.lastAccessed = new Timestamp(System.currentTimeMillis());
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Document(String identifier, 
                    String documentName, 
                    String documentLongName, 
                    String documentDescription, 
                    DocumentType documentType, 
                    Project project, 
                    Folder folder,
                    InformationClass infoClass,
                    String issue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.identifier = identifier;
        this.documentName = documentName;
        this.documentLongName = documentLongName;
        this.documentDescription = documentDescription;
        this.documentType = documentType;
        this.project = project;
        this.folder = folder;
        this.status = DocumentStatus.DRAFT;
        this.infoClass = infoClass;
        this.documentIssue = issue;
        this.lastAccessed = new Timestamp(System.currentTimeMillis());
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Document{" + "id=" + id + ", uuId=" + uuId + ", identifier=" + 
                identifier + ", documentName=" + documentName + ", documentLongName=" + 
                documentLongName + ", documentDescription=" + documentDescription + ", documentType=" + 
                documentType + ", infoClass=" + infoClass + ", documentIssue=" + 
                documentIssue + ", project=" + project + ", folder=" + folder + ", owner=" + 
                owner + ", status=" + status + ", creationDate=" + creationDate + ", modifiedDate=" +
                modifiedDate + '}';
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentLongName() {
        return documentLongName;
    }

    public void setDocumentLongName(String documentLongName) {
        this.documentLongName = documentLongName;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public InformationClass getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(InformationClass infoClass) {
        this.infoClass = infoClass;
    }

    public String getDocumentIssue() {
        return documentIssue;
    }

    public void setDocumentIssue(String documentIssue) {
        this.documentIssue = documentIssue;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
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

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<User> getDistributions() {
        return distributions;
    }

    public void setDistributions(List<User> distributions) {
        this.distributions = distributions;
    }
}

