/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.FolderType;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.ProjectType;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.FolderTypeRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectTypeRepository;


/**
 *
 * @author LukeS
 */
@RestController
public class ObjectTypeRestController {
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    
    @Autowired
    private FolderTypeRepository folderTypeRepository;
    
    @RequestMapping("/api/itemtypes/claimtype/list")
    public ResponseEntity<List<ClaimType>> getClaimTypesList(Model model) {
        
        List<ClaimType> claimTypes = new ArrayList<>();
        claimTypeRepository.findAll().forEach(claimTypes::add);
        
        return new ResponseEntity<List<ClaimType>>(claimTypes, HttpStatus.OK);
    }
    
    @RequestMapping("/api/itemtypes/infoclass/list")
    public ResponseEntity<List<InformationClass>> getInfoClassList(Model model) {
        
        List<InformationClass> infoClasses = infoClassRepository.findAllByOrderByClassNameAsc();
        
        return new ResponseEntity<List<InformationClass>>(infoClasses, HttpStatus.OK);
    }
    
    @RequestMapping("/api/itemtypes/projecttype/list")
    public ResponseEntity<List<ProjectType>> getProjectTypeList(Model model) {
        
        List<ProjectType> projectTypes = projectTypeRepository.findAllByOrderByTypeNameAsc();
//        List<ProjectType> projectTypes = new ArrayList<>();
//        projectTypeRepository.findAll().forEach(projectTypes::add);
        
        return new ResponseEntity<List<ProjectType>>(projectTypes, HttpStatus.OK);
    }
    
    @RequestMapping("/api/itemtypes/foldertype/list")
    public ResponseEntity<List<FolderType>> getFolderTypeList(Model model) {
        
        List<FolderType> folderTypes = new ArrayList<>();
        folderTypeRepository.findAll().forEach(folderTypes::add);
        
        return new ResponseEntity<List<FolderType>>(folderTypes, HttpStatus.OK);
    }
}
