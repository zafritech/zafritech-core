package org.zafritech.core.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity(name = "CORE_USERS")
public class User implements Serializable {
    
    private static long serialVersionUID = -687205007426664632L;

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String userName;

    private String password;

    private String firstName;

    private String lastName;
    
    private String mainRole;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "CORE_USER_ROLES",
               joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Role> userRoles = new HashSet<Role>();
    
    @ManyToOne
    @JoinColumn(name = "contactId")
    private Contact contact;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public User() {
        
    }

    public User(String email, String password) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.firstName = email.substring(0, 1).toUpperCase() + email.substring(1, email.indexOf('@')); 
        this.password = new BCryptPasswordEncoder().encode(password);
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String email, String password, HashSet<Role> roles) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.firstName = email.substring(0, 1).toUpperCase() + email.substring(1, email.indexOf('@')); 
        this.password = new BCryptPasswordEncoder().encode(password);
        this.userRoles = roles;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String email, String password, String firstName, String lastName, HashSet<Role> roles) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoles = roles;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String email, String password, String firstName, String lastName, Contact contact) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public User(String email, String password, String firstName, String lastName, Contact contact, HashSet<Role> roles) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.userRoles = roles;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    // For Json read from file
    public User(String email, String firstName, String lastName, String mainRole) {

        this.uuId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mainRole = mainRole;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "User{" + "id=" + id + ", uuId=" + uuId + ", email=" + email + ", userName=" + 
                userName + ", firstName=" + firstName + ", lastName=" + 
                lastName + ", mainRole=" + mainRole + ", userRoles=" + 
                userRoles + ", contact=" + contact + '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMainRole() {
        return mainRole;
    }

    public void setMainRole(String mainRole) {
        this.mainRole = mainRole;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

