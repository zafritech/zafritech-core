/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.services.UserService;
import org.zafritech.requirements.data.dao.TemplateItemToJsonDao;
import org.zafritech.requirements.data.dao.TemplateToJsonDao;
import org.zafritech.requirements.data.domain.Template;
import org.zafritech.requirements.data.domain.TemplateItem;
import org.zafritech.requirements.data.repositories.TemplateItemRepository;
import org.zafritech.requirements.data.repositories.TemplateRepository;
import org.zafritech.requirements.enums.MediaType;

/**
 *
 * @author LukeS
 */
@Component
public class DocumentTemplatesInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Value("${zafritech.adminuser.email}")
    private String admin_email;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Autowired
    private TemplateRepository templateRepository;
    
    @Autowired
    private TemplateItemRepository templateItemRepository;
    
    @Autowired
    private UserService userService;
         
    @Transactional
    public void init() {
        
        String path = new File(data_dir + "templates").getAbsolutePath();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        for (File file : listOfFiles) {
        
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                
                TemplateToJsonDao dao = mapper.readValue(file, TemplateToJsonDao.class);
                
                Template template = new Template(dao.getLongName(),
                                                 dao.getLongName(),
                                                 dao.getDescription(),
                                                 descriptorRepository.findByDescriptorCode(dao.getContent()),
                                                 entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY", dao.getType()),
                                                 userService.findByEmail(admin_email)); 
                
                template = templateRepository.save(template);
                
                for (TemplateItemToJsonDao templateItemnDao : dao.getItems()) {
                    
                    templateItemRepository.save(new TemplateItem(templateItemnDao.getSystemId(),
                                                                 templateItemnDao.getItemClass(),
                                                                 templateItemnDao.getItemLevel(),
                                                                 (!templateItemnDao.getItemNumber().isEmpty()) ? templateItemnDao.getItemNumber() : null,
                                                                 templateItemnDao.getItemValue(),
                                                                 entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("ITEM_TYPE_ENTITY", templateItemnDao.getItemType()),
                                                                 MediaType.valueOf(templateItemnDao.getMediaType()),
                                                                 templateItemnDao.getParentSystemId(),
                                                                 template,
                                                                 templateItemnDao.getSortIndex()));

                }
                
            } catch (IOException ex) {

                Logger.getLogger(CompaniesInit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
