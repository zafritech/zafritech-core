/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zafritech.core.data.domain.LibraryItem;
import org.zafritech.core.data.repositories.LibraryItemRepository;

/**
 *
 * @author LukeS
 */
@Controller
public class LibraryController {

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    
    @RequestMapping(value = {"/library", "/library/list"})
    public String LibraryReferenceItemsList(Model model) {
        
        model.addAttribute("titles", "titles");
        
        return "views/library/index";
    }
    
    @RequestMapping(value="/library/items/open/{uuid}", method=RequestMethod.GET)
    public void openLibraryReferenceItem(HttpServletResponse response,
                                         @PathVariable(value = "uuid") String uuid) throws IOException {
        
        LibraryItem libraryItem = libraryItemRepository.findByUuId(uuid);
                
        String referencePath = data_dir + libraryItem.getItemPath();
        File file = new File(referencePath);
        String fileExtension = FilenameUtils.getExtension(referencePath);
        
        response.addHeader("content-disposition", "attachment;filename=" + libraryItem.getIdentifier() + "." + fileExtension); 
        response.setContentType("application/octet-stream"); 
        
        InputStream inputStream = new FileInputStream(file);
        IOUtils.copy(inputStream, response.getOutputStream()); 
        response.flushBuffer();
    }
}
