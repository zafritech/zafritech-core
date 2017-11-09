package org.zafritech.requirements.data.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.zafritech.requirements.enums.ItemStatus;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.zafritech.core.data.domain.BaseLine;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.requirements.enums.MediaType;

@Indexed
@Entity(name = "REQUIREMENTS_ITEMS")
@AnalyzerDef(
    name = "hyphenanalyzer",
    tokenizer = @TokenizerDef(factory = ClassicTokenizerFactory.class),
    filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
    }
)
public class Item implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    @Field
    @Analyzer(definition = "hyphenanalyzer")
    @Column(nullable = false)
    private String systemId;

    @Column(columnDefinition = "TEXT")
    private String itemClass;

    @Field
    @Analyzer(definition = "hyphenanalyzer")
    private String identifier;

    private String itemNumber;

    @Field(store = Store.NO)
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String itemValue;

    @ManyToOne
    @JoinColumn(name = "itemTypeId")
    private EntityType itemType;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
     
    @ManyToOne
    @JoinColumn(name = "vvMethodId")
    private EntityType verificationMethod;

    private String itemCaption;

    @ManyToOne
    @JoinColumn(name = "documentId")
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Item parent;
            
    @ManyToOne
    @JoinColumn(name = "itemCategoryId")
    private ItemCategory itemCategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dstItemId")
    @JsonManagedReference
    private List<Link> inLinks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "srcItemId")
    @JsonManagedReference
    private List<Link> outLinks = new ArrayList<>();

    private boolean linkChanged;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "itemId")
    @JsonManagedReference
    @OrderBy("creationDate DESC")
    private List<ItemComment> comments = new ArrayList<>();

    private int itemLevel;

    private int sortIndex;

    private int itemVersion;
    
    @ManyToOne
    @JoinColumn(name = "baseLineId")
    private BaseLine baseLine;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    public Item() {
    
    }

    public Item(String systemId,
            String itemValue,
            EntityType itemType,
            Document document,
            Item parent) {

        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.itemValue = itemValue;
        this.itemType = itemType;
        this.mediaType = MediaType.TEXT;
        this.document = document;
        this.parent = parent;
        this.itemVersion = 0;
        this.itemStatus = ItemStatus.ITEM_STATUS_CREATED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Item(String systemId,
            String identifier,
            String itemValue,
            EntityType itemType,
            MediaType mediaType,
            Document document,
            Item parent) {

        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.identifier = identifier;
        this.itemValue = itemValue;
        this.itemType = itemType;
        this.mediaType = mediaType;
        this.document = document;
        this.parent = parent;
        this.itemVersion = 0;
        this.itemStatus = ItemStatus.ITEM_STATUS_CREATED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Item{" + "id=" + id + ", uuId=" + uuId + ", systemId=" + systemId + 
                ", itemClass=" + itemClass + ", identifier=" + identifier + 
                ", itemNumber=" + itemNumber + ", itemValue=" + itemValue + ", itemType=" + 
                itemType + ", mediaType=" + mediaType + ", verificationMethod=" +
                verificationMethod + ", itemCaption=" + itemCaption + ", document=" + 
                document + ", parent=" + parent + ", itemCategory=" + itemCategory + 
                ", inLinks=" + inLinks + ", outLinks=" + outLinks + ", linkChanged=" + 
                linkChanged + ", comments=" + comments + ", itemLevel=" + itemLevel + 
                ", sortIndex=" + sortIndex + ", itemVersion=" + itemVersion + ", baseLine=" + 
                baseLine + ", itemStatus=" + itemStatus + ", creationDate=" + creationDate + 
                ", modifiedDate=" + modifiedDate + '}';
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public EntityType getItemType() {
        return itemType;
    }

    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    public EntityType getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(EntityType verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getItemCaption() {
        return itemCaption;
    }

    public void setItemCaption(String itemCaption) {
        this.itemCaption = itemCaption;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public List<Link> getInLinks() {
        return inLinks;
    }

    public void setInLinks(List<Link> inLinks) {
        this.inLinks = inLinks;
    }

    public List<Link> getOutLinks() {
        return outLinks;
    }

    public void setOutLinks(List<Link> outLinks) {
        this.outLinks = outLinks;
    }

    public boolean isLinkChanged() {
        return linkChanged;
    }

    public void setLinkChanged(boolean linkChanged) {
        this.linkChanged = linkChanged;
    }

    public List<ItemComment> getComments() {
        return comments;
    }

    public void setComments(List<ItemComment> comments) {
        this.comments = comments;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public int getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(int itemVersion) {
        this.itemVersion = itemVersion;
    }

    public BaseLine getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(BaseLine baseLine) {
        this.baseLine = baseLine;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
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

