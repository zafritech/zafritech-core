///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.zafritech.core.data.initializr;
//
//import java.util.HashSet;
//import javax.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.zafritech.requirements.data.domain.LinkType;
//import org.zafritech.requirements.data.repositories.LinkTypeRepository;
//
///**
// *
// * @author LukeS
// */
//@Component
//public class LinkTypesInit {
//    
//    @Autowired
//    private LinkTypeRepository linkTypeRepository;
//     
//    @Transactional
//    public void init() {
//        
//        linkTypeRepository.save(new HashSet<LinkType>() {
//            {
//                add(new LinkType("Affected", "Is Affected By", "Captures the relationship between a requirement artifact and a change management item that affects the implementation of the requirement artifact. For example, a defect in the Change and Configuration Management (CCM) application can affect the implementation of a requirement artifact. In the CCM application, links of this type are shown as Affects links.", false));
//                add(new LinkType("Satisfies", "Satisfies or Is satisfied By", "Captures how the different levels of requirements are elaborated. For example: an approved vision statement in a vision document can be satisfied by one or more stakeholder requirements.", false));
//                add(new LinkType("Validates", "Validates", "Captures the relationship between a requirement artifact and a test artifact that validates the implementation of the requirement artifact. For example, a test plan in the Quality Management (QM) application can validate the implementation of a requirement artifact. In the QM application, links of this type are displayed as Validates links.", false));
//                add(new LinkType("Constrained", "Is Constrained By", "Captures the relationship between requirement artifacts when one artifact limits or holds back the other artifact. For example, an artifact can be constrained by a requirement that it must conform to", false));
//                add(new LinkType("Tracks", "Tracks or Is Tracked By", "Captures the relationship between a requirement artifact and a change management item that tracks the implementation of the requirement artifact. For example, a task in the CCM application can track the implementation of a requirement artifact. In the CCM application, links of this type are shown as Tracks links.", false));
//                add(new LinkType("Refines", "Refines", "", false));
//                add(new LinkType("Links", "Links To", "Tracks a general relationship between requirement artifacts.", false));
//                add(new LinkType("Embedded", "Is Embedded In", "Tracks a containment relationship between RM artifacts. These types of relationships occur when you complete operations such as inserting an artifact and inserting an image for a text artifact.", false));
//                add(new LinkType("Refers", "Refers To", "", false));
//                add(new LinkType("Derived", "Is Derived From", "Captures the relationship between a requirement artifact and an architecture management item that represents a model of the requirement artifact. For example, a UML use case in an architecture management application can represent a requirement artifact. In architecture management applications, links of this type are shown as Derives From Architecture Element links.", true));
//                add(new LinkType("Implements", "Implements", "Captures the relationship between a requirement artifact and a change management item that describes the implementation of the requirement artifact. For example, a story in the CCM application can describe the implementation of a requirement artifact. In the CCM application, links of this type are shown as Implements links.", false));
//                add(new LinkType("Extracted", "Is Extracted From", "Captures when the content of a requirement artifact was created from the contents of another requirement artifact. This type of link is created during extraction-based operations; for example, when you create an artifact by saving an existing artifact as a new artifact.", false));
//                add(new LinkType("Mitigates", "Mitigates", "Captures the relationship between requirements and risks. A requirement mitigates one or more risks, and a risk is mitigated by one or more requirements.", false));
//                add(new LinkType("Decomposition", "Is a Decomposition Of", "Captures part-whole relationships between requirement artifacts. Typically, these types of links represent artifact hierarchies.", false));
//                add(new LinkType("Referenced", "Referenced By or References", "Tracks a relationship between requirement artifacts. These types of relationships occur when creating links between artifacts.", false));
//                add(new LinkType("Illustrates", "Illustrates", "Illustrates the relationships between graphical and text artifacts.", false));
//            }
//        });
//    }
//}
