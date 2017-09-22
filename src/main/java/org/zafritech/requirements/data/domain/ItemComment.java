package org.zafritech.requirements.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.zafritech.core.data.domain.User;
import org.zafritech.requirements.enums.ItemStatus;

@Indexed
@Entity(name = "REQUIREMENTS_ITEM_COMMENTS")
public class ItemComment implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    @ManyToOne
    @JoinColumn(name = "itemId")
    @JsonBackReference
    private Item item;

    @Field(store = Store.NO)
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ItemComment() {
        
    }

    public ItemComment(Item item, String comment) {

        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.comment = comment;
        this.status = ItemStatus.ITEM_STATUS_CREATED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ItemComment(Item item, String comment, User author) {

        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.comment = comment;
        this.status = ItemStatus.ITEM_STATUS_CREATED;
        this.author = author;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "ItemComment{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", item=" + getItem() + ", comment=" + 
                getComment() + ", author=" + getAuthor() + ", creationDate=" + 
                getCreationDate() + '}';
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

