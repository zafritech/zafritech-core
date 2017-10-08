/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.ReferenceDao;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentReference;
import org.zafritech.core.data.domain.LibraryItem;
import org.zafritech.core.data.domain.Reference;
import org.zafritech.core.data.domain.UrlLink;
import org.zafritech.core.data.repositories.DocumentReferenceRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.LibraryItemRepository;
import org.zafritech.core.data.repositories.ReferenceRepository;
import org.zafritech.core.data.repositories.UrlLinkRepository;
import org.zafritech.core.enums.ReferenceSources;
import org.zafritech.core.enums.ReferenceTypes;
import org.zafritech.core.services.ReferenceService;

/**
 *
 * @author LukeS
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentReferenceRepository documentReferenceRepository;
    
    @Autowired
    private UrlLinkRepository urlLinkRepository;
    
    @Autowired
    private LibraryItemRepository libRepository;
    
    @Override
    public Reference addDocumentReference(ReferenceDao refDao) {
        
        Reference reference = null; 
        DocumentReference documentReference;
        Document document = documentRepository.findOne(refDao.getDocumentId());
        String docRefId = getNextDocumentReferenceId(document);
                
        switch(refDao.getSource()) {
            
            case "PROJECT":
                
                Document refDocument = documentRepository.findOne(refDao.getProjectRefId());
                
                reference = referenceRepository.findBySourceTypeAndIdValue(ReferenceSources.valueOf(refDao.getSource()), refDao.getProjectRefId());
                
                if (reference == null) {
                    
                    reference = new Reference(ReferenceSources.valueOf(refDao.getSource()), 
                                              refDao.getProjectRefId(),
                                              refDocument.getIdentifier(), 
                                              refDocument.getDocumentLongName(), 
                                              refDocument.getDocumentIssue(),
                                              refDocument.getProject().getProjectSponsor().getCompanyShortName());
                    
                    reference = referenceRepository.save(reference);
                }

                break;
                
            case "LIBRARY":
            
                reference = referenceRepository.findBySourceTypeAndIdValue(ReferenceSources.valueOf(refDao.getSource()), refDao.getLibraryRefId());
                
                if (reference == null) {
                    
                    LibraryItem libItem = libRepository.findOne(refDao.getLibraryRefId());
                    
                    reference = new Reference(ReferenceSources.valueOf(refDao.getSource()), 
                                              refDao.getLibraryRefId(),
                                              libItem.getIdentifier(),
                                              libItem.getItemTitle(),
                                              libItem.getVersion(),
                                              libItem.getAuthors());
                    
                    reference = referenceRepository.save(reference);
                }
                
                break;
                
            case "URL_LINK":

                UrlLink link = new UrlLink(refDao.getLinkRefTitle(), refDao.getLinkRefUrl(), refDao.getLinkRefAuthority());
                link.setIdentifier(refDao.getLinkRefIdentifier()); 

                link = urlLinkRepository.save(link);

                reference = new Reference(ReferenceSources.valueOf(refDao.getSource()), 
                                          link.getId(),
                                          link.getIdentifier(), 
                                          link.getLinkTitle(), 
                                          "N/A",
                                          link.getSourceAuthority());

                reference = referenceRepository.save(reference);

                break;
        }

        documentReference = new DocumentReference(docRefId,
                                                  reference, 
                                                  documentRepository.findOne(refDao.getDocumentId()), 
                                                  ReferenceTypes.valueOf(refDao.getReferenceType())); 

        documentReferenceRepository.save(documentReference);

        return reference;
    }
    
    private String getNextDocumentReferenceId(Document document) {
        
        String docRefId = "RD 01";
        
        DocumentReference docReference = documentReferenceRepository.findFirstByDocumentOrderByDocRefIdDesc(document);
        
        if (docReference != null) {
        
            String docRefString = docReference.getDocRefId();
            String[] docRefArray = docRefString.split("\\s+");
            docRefId = docRefArray[0] + " " + String.format("%02d", Integer.valueOf(docRefArray[1]) + 1);
            
        }
        
        return docRefId;
    }
}
