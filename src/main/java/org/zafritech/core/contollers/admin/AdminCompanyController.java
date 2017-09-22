/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.services.CommonService;
import org.zafritech.core.services.CompanyService;

/**
 *
 * @author LukeS
 */
@Controller
public class AdminCompanyController {
    
    @Autowired
    private CommonService commonService;
     
    @Autowired
    private CompanyRepository companyRepository;
    
     
    @Autowired
    private CompanyService companyService;
    
    @RequestMapping(value = {"/admin/companies", "/admin/companies/list"})
    public String createMessageTemplate(@RequestParam(name = "s", defaultValue = "15") int pageSize,
                                        @RequestParam(name = "p", defaultValue = "1") int pageNumber,
                                        Model model) {
        
        PageNavigationDao navigator = commonService.getPageNavigator(companyRepository.findAll().size(), pageSize, pageNumber);

        model.addAttribute("companies", companyService.findOrderByCompanyName(pageSize, pageNumber));
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("coCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());
        
        return "views/admin/companies/list";
    }
    
    @RequestMapping("/admin/companies/{uuid}")
    public String getUser(@PathVariable String uuid, Model model) {
        
        Company company = companyRepository.getByUuId(uuid);
        
        model.addAttribute("company", company);
        
        return "views/admin/companies/profile";
    }
}
