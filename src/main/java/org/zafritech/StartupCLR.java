/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zafritech.core.services.InitService;

/**
 *
 * @author LukeS
 */
@Component
public class StartupCLR implements CommandLineRunner {

    @Autowired
    private InitService initService;
   
    @Override
    public void run(String... strings) throws Exception {
        
        initService.initEntityTypes();
        initService.initTemplateVariables();
        initService.initRoles();
        initService.initLocales();
        initService.initSIUnits();
        initService.initCountries();
        initService.initUsers();
        initService.initClaimTypes();
        initService.initInfoClasses();
        initService.initCompanies();
        initService.initDocumentContentDescriptors();
        initService.initProjects();
        initService.initFolders();
        initService.initDocuments();
        initService.initLibrary();
        initService.initSystemVariable();
        initService.initSnaphots();
    }
}
