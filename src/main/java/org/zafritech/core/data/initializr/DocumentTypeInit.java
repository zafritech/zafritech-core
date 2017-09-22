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
import org.zafritech.core.data.domain.DocumentType;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.DocumentTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class DocumentTypeInit {
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Transactional
    public void init() {
        
        documentTypeRepository.save(new HashSet<DocumentType>() {{ 

                add(new DocumentType("GEN", "Generic Project Documennt", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "This document type is for a generic project document that does not fall under specifications. It may or may not contain requirements. Plans, reports, procudures etc. fall under this category."));
                add(new DocumentType("RLS", "Requirements List", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "This document is a preliminary collection of system requirements that are discovered from interection with stakeholders. The requirements act as an input to the formal SyRS."));
                add(new DocumentType("URS", "User Requirements Specification", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "A User Requirements Specification (a.k.a. Stakeholder Requirements Specification) specifies the requirements the user expects from the system to be constructed. User Requirements are the top level of requirements. They capture the needs of users, the customer and other sources of requirements like legal regulations and internal company high level requirements."));
                add(new DocumentType("SYS", "System Requirements Specification", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "A System Requirements Specification contains the next level of requirements after user requirements. The aim of system requirements is to set precise technical requirements for the system development. System requirements are derived from user requirements by considering existing technology, components and so on."));
                add(new DocumentType("VRS", "Verification Requirements Specification", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "The Verification Requirements Specification (VRS) describes the qualities of the evidence required that a set of requirements defining an item is satisfied. The item may be of any nature whatsoever, ranging from, for example, a physical object, to software, to an interface, to a data item, to a material, or to a service."));
                add(new DocumentType("SYD", "System Design Description", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "The System/Subsystem Design Description (aka SSDD) describes the system- or subsystem-wide design and the architectural design of a system or subsystem. The SyDD may be supplemented by Interface Design Descriptions  and Database Design Descriptions."));
                add(new DocumentType("IRS", "Interface Requirements Specification", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "Interface Requirements Specification (IRS) specifies the requirements imposed on one or more systems, subsystems, Hardware Configuration Items (HWCIs), Computer Software Configuration Items (CSCIs), manual operations, or other system components to achieve one or more interfaces among these entities."));
                add(new DocumentType("ICD", "Interface Control Document", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "Interface Control Document (ICD) is a document that describes the interface(s) to a system or subsystem. It may describe the inputs and outputs of a single system or the interface between two systems or subsystems. It can be very detailed or pretty high level, but the point is to describe all inputs to and outputs from a system"));
                add(new DocumentType("SRS", "Software Requirements Specification", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "A software requirements specification (SRS) is a description of a software system to be developed. It lays out functional and non-functional requirements, and may include a set of use cases that describe user interactions that the software must provide."));
                add(new DocumentType("SDD", "Software Design Description", descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS"), "A software design description (aka software design document or SDD) is a written description of a software product, that a software designer writes in order to give a software development team overall guidance to the architecture of the software project."));
            }
        });
    }
}
