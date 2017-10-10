/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.converters;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.ProjectStatus;

/**
 *
 * @author LukeS
 */
@Component
public class DaoToProjectConverter implements Converter<ProjectDao, Project> {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private ContactRepository contactRepository;
 
    @Autowired
    private ProjectRepository projectRepository;
 
    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private InformationClassRepository infoClassRepository;

    @Autowired
    private UserRepository userRepository;
      
    @Override
    public Project convert(ProjectDao projectDao) {
  
        System.out.println("\r\n");
        System.out.println("DAO ID: " + projectDao.getId());
        System.out.println("\r\n");
            
        Project project = new Project(projectDao.getProjectNumber(),
                                      projectDao.getProjectName(), 
                                      projectDao.getProjectShortName(),
                                      companyRepository.findOne(projectDao.getCompanyId()), 
                                      infoClassRepository.findOne(projectDao.getInfoClassId()));


        project.setProjectType(entityTypeRepository.findOne(projectDao.getProjectTypeId())); 
        project.setStartDate(Timestamp.valueOf(projectDao.getStartDate())); 
        project.setEndDate(Timestamp.valueOf(projectDao.getEndDate())); 
        project.setStatus(ProjectStatus.valueOf(projectDao.getStatus())); 
        project.setProjectContact(contactRepository.findOne(projectDao.getContactId()));  
        project.setProjectManager(userRepository.findOne(projectDao.getManagerId())); 
        project.setProjectDescription(projectDao.getProjectDescription());
        
        if (projectDao.getId() != null) {
            
            project = projectRepository.findOne(projectDao.getId());
        } 
        
        return project;
    }
}
