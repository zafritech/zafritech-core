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
import org.zafritech.core.enums.CompanyRole;

@Entity(name = "CORE_COMPANIES")
public class Company implements Serializable {

    private static final long serialVersionUID = -2569109922025524359L;

    @Id
    @GeneratedValue
    private Long Id;

    private String uuId;

    private String companyName;

    private String companyCode;

    private String companyShortName;

    @Enumerated(EnumType.STRING)
    private CompanyRole companyRole;

    @Column(columnDefinition = "TEXT")
    private String companyRoleDescription;

    private byte[] companyLogo;

    @ManyToOne
    @JoinColumn(name = "contactId")
    private Contact contact;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Company() {
        
    }

    public Company(String name, String shortName) {

        this.uuId = UUID.randomUUID().toString();
        this.companyName = name;
        this.companyCode = name.replaceAll("\\s+","").substring(0, 3).toUpperCase();
        this.companyShortName = shortName;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Company(String name, String shortName, Contact contact) {

        this.uuId = UUID.randomUUID().toString();
        this.companyName = name;
        this.companyCode = name.replaceAll("\\s+","").substring(0, 3).toUpperCase();
        this.companyShortName = shortName;
        this.contact = contact;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Company(String name, String shortName, Contact contact, CompanyRole role) {

        this.uuId = UUID.randomUUID().toString();
        this.companyName = name;
        this.companyCode = name.replaceAll("\\s+","").substring(0, 3).toUpperCase();
        this.companyShortName = shortName;
        this.contact = contact;
        this.companyRole = role;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Company(String name, String shortName, CompanyRole role, String roleDescription, Contact contact) {

        this.uuId = UUID.randomUUID().toString();
        this.companyName = name;
        this.companyCode = name.replaceAll("\\s+","").substring(0, 3).toUpperCase();
        this.companyShortName = shortName;
        this.companyRole = role;
        this.companyRoleDescription = roleDescription;
        this.contact = contact;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Company{" + "Id=" + Id + ", uuId=" + uuId + ", companyName=" + 
                companyName + ", companyCode=" + companyCode + ", companyShortName=" + 
                companyShortName + ", companyRole=" + companyRole + ", companyRoleDescription=" + 
                companyRoleDescription + ", contact=" + contact + ", creationDate=" + 
                creationDate + ", modifiedDate=" + modifiedDate + '}';
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public CompanyRole getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(CompanyRole companyRole) {
        this.companyRole = companyRole;
    }

    public String getCompanyRoleDescription() {
        return companyRoleDescription;
    }

    public void setCompanyRoleDescription(String companyRoleDescription) {
        this.companyRoleDescription = companyRoleDescription;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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

