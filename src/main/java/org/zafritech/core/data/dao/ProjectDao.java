/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author LukeS
 */
public class ProjectDao {

    private Long id;
    
    private String uuId;

    private Long companyId;
    
    private Long projectTypeId;
    
    private Long infoClassId;
    
    private String projectNumber;
    
    private String projectName;

    private String projectShortName;

    private String projectDescription;
    
    private Long contactId;
    
    private Long managerId;

    private String status;
    
    private String startDate;
    
    private String endDate;

    public ProjectDao() {
        
    }

    @Override
    public String toString() {
        
        return "ProjectDao{" + "id=" + id + ", companyId=" + 
                companyId + ", projectTypeId=" + projectTypeId + ", infoClassId=" + 
                infoClassId + ", projectNumber=" + projectNumber + ", projectName=" + 
                projectName + ", projectShortName=" + 
                projectShortName + ", projectDescription=" + 
                projectDescription + ", contactId=" + contactId + ", managerId=" + 
                managerId + ", status=" + status + ", startDate=" + 
                startDate + ", endDate=" + endDate + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public Long getInfoClassId() {
        return infoClassId;
    }

    public void setInfoClassId(Long infoClassId) {
        this.infoClassId = infoClassId;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
}
