/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.HashSet;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.requirements.data.domain.ItemType;
import org.zafritech.requirements.data.repositories.ItemTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class ItemTypesInit {
    
    @Autowired
    private ItemTypeRepository itemTypeRepository;
     
    @Transactional
    public void init() {
        
        itemTypeRepository.save(new HashSet<ItemType>() {
            {

                add(new ItemType("State/Mode", "STMD", "States/Modes Requirement", ""));
                add(new ItemType("Functional", "FCNL", "Functional Requirement", "A functional requirement describes a function of the system, i.e. what the system must do resp. a service the system provides to its users."));
                add(new ItemType("Performance", "PERF", "Performance Requirement", "A performance requirement is a non-functional requirement that describes the amount of useful work accomplished by the system compared to the time and resources used."));
                add(new ItemType("Constraint", "CONS", "Constraint Requirement", "Constraint Requirement describes real-world limits or boundaries around what we want to happen"));
                add(new ItemType("Design", "DSGN", "Design Requirement", "Design requirements direct the design (internals of the system), by inclusion (build it this way), or exclusion (don't build it this way)."));
                add(new ItemType("Scalability", "SCAL", "Scalability Requirement", "A scalability requirement is a non-functional requirement that describes the ability of the system to handle growing amounts of work in a graceful manner or its ability to be enlarged to accommodate that growth."));
                add(new ItemType("Security", "SCRT", "Security Requirement", "A security requirement is a non-functional requirement that describes the stipulated degree of the systems protection against danger, damage, misuse, unauthorized access, data loss and crime."));
                add(new ItemType("Maintainability", "MANT", "Maintainability Requirement", "A maintainability requirement is a non-functional requirement that describes the ease with which the system can be maintained in order to isolate defects or their cause, correct defects or their cause, meet new requirements, make future maintenance easier, or cope with a changed environment."));
                add(new ItemType("Interface", "INRF", "External Interface Requirement", ""));
                add(new ItemType("Environment", "ENVR", "Environment Requirement", ""));
                add(new ItemType("Resource", "RESC", "Resource Requirement", ""));
                add(new ItemType("Physical", "PHYL", "Physical Requirement", ""));
                add(new ItemType("Usability", "USBL", "Usability Requirement", "A usability requirement is a non-functional requirement describing the intended ease of use (ergonomical comfort) and learnability of the system."));
                add(new ItemType("Legal", "LEGL", "Legal Requirement", "A legal requirement is a non-functional requirement that states a regulation that must be recognized by the system. Regulations could be laws, standards, specifications, etc."));
                add(new ItemType("Story", "STRY", "User Story", "A (user) story is a special kind of functional requirement, which uses one or more sentences in the everyday or business language of the end user that captures what the user (resp. a role) wants to achieve. User stories generally follow the following template: \"As a <role>, I want <goal/desire> so that <benefit>.\""));
                add(new ItemType("Unclassified", "UNCL", "Unclassified Requirement", "Requirements not classified."));
                add(new ItemType("None", "NONE", "Not a Requirement", "Prose textual information for clarification and context setting for a set of requirements."));
                add(new ItemType("Other Quality", "OTHR", "Other Quality Requirement", ""));
                add(new ItemType("Operational", "OPER", "Operational Requirement", ""));
                add(new ItemType("Adaptability", "ADPT", "Adaptability Requirement", ""));
                add(new ItemType("Logistical", "LOGS", "Logistical Requirement", ""));
                add(new ItemType("Policy", "POLY", "Policy and Regulations", ""));
                add(new ItemType("Cost/Schedule", "COST", "Cost and Schedule Constraint", ""));
            }
        });
    }
}
