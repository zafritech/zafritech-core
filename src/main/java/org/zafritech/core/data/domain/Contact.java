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

@Entity(name = "CORE_CONTACTS")
public class Contact implements Serializable {

    private static long serialVersionUID = 4669091304090562573L;

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    private String email;

    private String firstName;

    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String address;
    
    private String city;
    
    private String state;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;
    
    private String postCode;

    private String phone;
    
    private String mobile;

    private String website;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Contact() {
        
    }

    public Contact(String email) {
        
        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Contact(String email, Country country) {
        
        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.country = country;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Contact(String email, Country country, String city) {
        
        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.country = country;
        this.city = city;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Contact(String firstName, 
                   String lastName, 
                   String email, 
                   String website, 
                   Country country, 
                   String state, 
                   String city) {
        
        this.uuId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.website = website;
        this.country = country;
        this.state = state;
        this.city = city;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Contact(String email, 
                   String firstName, 
                   String lastName, 
                   String address, 
                   String city, 
                   String state, 
                   Country country, 
                   String postCode, 
                   String phone, 
                   String mobile, 
                   String website) {
        
        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postCode = postCode;
        this.phone = phone;
        this.mobile = mobile;
        this.website = website;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Contact{" + "id=" + id + ", uuId=" + uuId + ", email=" + 
                email + ", firstName=" + firstName + ", lastName=" + 
                lastName + ", address=" + address + ", city=" + city + ", state=" + 
                state + ", country=" + country + ", postCode=" + postCode + ", phone=" + 
                phone + ", mobile=" + mobile + ", website=" + 
                website + ", creationDate=" + creationDate + ", modifiedDate=" + 
                modifiedDate + '}';
    }

    
    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

