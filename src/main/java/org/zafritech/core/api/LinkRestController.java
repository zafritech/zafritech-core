/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.requirements.data.dao.LinkCreateDao;
import org.zafritech.requirements.data.dao.LinkDao;
import org.zafritech.requirements.data.domain.Link;
import org.zafritech.requirements.services.LinkService;

/**
 *
 * @author LukeS
 */
@RestController
public class LinkRestController {
    
    @Autowired
    private LinkService linkService;
    
    @RequestMapping(value = "/api/requirements/document/items/links/ref/dao/{id}", method = RequestMethod.GET)
    public LinkCreateDao createItemLink(@PathVariable(value = "id") Long itemId) {

        LinkCreateDao createDao = linkService.getDaoForLinkCreation(itemId);
        
        return createDao;
    }
    
    @RequestMapping(value = "/api/requirements/document/items/link/save", method = RequestMethod.POST)
    public ResponseEntity<Link> saveItemLink(@RequestBody LinkDao linkDao) {
        
        Link link = linkService.saveRquirementsLink(linkDao);

        return new ResponseEntity<Link>(link, HttpStatus.OK);
    }
}
