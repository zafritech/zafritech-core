/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import org.zafritech.core.data.domain.RunOnceTask;

/**
 *
 * @author LukeS
 */
public interface InitService {
    
    RunOnceTask initEntityTypes();
    
    RunOnceTask initTemplateVariables();
    
    RunOnceTask initRoles();
    
    RunOnceTask initUsers();
    
    RunOnceTask initClaimTypes();
    
    RunOnceTask initLocales();
    
    RunOnceTask initSIUnits();
    
    RunOnceTask initCountries();
    
    RunOnceTask initInfoClasses();
    
    RunOnceTask initCompanies();
    
    RunOnceTask initFolders();
    
    RunOnceTask initDocumentContentDescriptors();
    
    RunOnceTask initProjects();
    
    RunOnceTask initDocumentTemplates();
    
    RunOnceTask initDocuments();
    
    RunOnceTask initLibrary();
    
    RunOnceTask initSnaphots();
}
