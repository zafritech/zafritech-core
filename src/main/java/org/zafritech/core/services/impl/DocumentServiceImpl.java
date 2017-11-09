/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.BaseLineDao;
import org.zafritech.core.data.dao.DefinitionDao;
import org.zafritech.core.data.dao.DocDao;
import org.zafritech.core.data.dao.DocEditDao;
import org.zafritech.core.data.domain.BaseLine;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Locale;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectWbsPackage;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.BaseLineRepository;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DefinitionRepository;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.LocaleRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.SystemVariableRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.DefinitionTypes;
import org.zafritech.core.enums.DocumentStatus;
import org.zafritech.core.enums.SystemVariableTypes;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.DocumentService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private LocaleRepository localeRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private EntityTypeRepository entitytTypeRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private FolderRepository folderRepository;
     
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DefinitionRepository definitionRepository;
          
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimRepository claimRepository;
     
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private SystemVariableRepository sysvarRepository;
    
    @Autowired
    private BaseLineRepository baseLineRepository;
    
    @Override
    public Document saveDao(DocDao docDao) {
        
        User owner = userService.loggedInUser();
        
        Document document = documentRepository.save(new Document(
        
                docDao.getIdentifier(),
                docDao.getDocumentName(),
                docDao.getDocumentLongName(),
                entitytTypeRepository.findOne(docDao.getTypeId()),
                descriptorRepository.findOne(docDao.getDecriptorId()),
                projectRepository.findOne(docDao.getProjectId()),
                wbsPackageRepository.findOne(docDao.getWbsId()),
                folderRepository.findOne(docDao.getFolderId()),
                infoClassRepository.findOne(docDao.getInfoClassId()),
                "0A"
        ));
        
        document.setDocumentLongName(docDao.getDocumentLongName());
        document.setDocumentDescription(docDao.getDocumentDescription());
        document.setOwner(owner);
        document.setDocumentIssue("0A");
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document;
    }

    @Override
    public Document duplicate(Long id) {
        
        User owner = userService.loggedInUser();
        Document oldDoc = documentRepository.findOne(id);
        
        Document document = new Document(
        
                oldDoc.getIdentifier() + "-COPY",
                oldDoc.getDocumentName() + " - Copy",
                oldDoc.getDocumentLongName(),
                oldDoc.getDocumentType(),
                oldDoc.getContentDescriptor(),
                oldDoc.getProject(),
                oldDoc.getWbs(),
                oldDoc.getFolder(),
                oldDoc.getInfoClass(),
                "0A"
        );
        
        document.setDocumentIssue(oldDoc.getDocumentIssue()); 
        document.setOwner(owner); 
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document;
    }

    @Override
    public Document saveEditDao(DocEditDao docEditDao) {
        
        User loginUser = userService.loggedInUser();
        User owner = (docEditDao.getOwnerId() != null) ? userRepository.findOne(docEditDao.getOwnerId()) : loginUser;
        
        Document document = documentRepository.findOne(docEditDao.getId());
        
        document.setIdentifier(docEditDao.getIdentifier());
        document.setDocumentName(docEditDao.getDocumentName());
        document.setDocumentLongName(docEditDao.getDocumentLongName());
        document.setDocumentDescription(docEditDao.getDocumentDescription());
        document.setDocumentIssue(docEditDao.getDocumentIssue()); 
        document.setStatus(DocumentStatus.valueOf(docEditDao.getStatus()));
        
        document.setProject(projectRepository.findOne(docEditDao.getProjectId()));
        document.setFolder(folderRepository.findOne(docEditDao.getFolderId()));
        document.setDocumentType(entitytTypeRepository.findOne(docEditDao.getTypeId()));
        document.setInfoClass(infoClassRepository.findOne(docEditDao.getInfoClassId()));
        document.setOwner(owner); 
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document; 
    }

    @Override
    public void initNewProjectDocuments(Project project, User user) {

        Folder root = folderRepository.findFirstByProjectAndFolderType(project, entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"));
        List<Folder> folders = folderRepository.findByParent(root);

        EntityType docType;
        String ident;
        String documentName;
        String documentLongName;
        ProjectWbsPackage wbs;
        String identRoot;

        InformationClass infoClass = infoClassRepository.findByClassCode("INFO_OFFICIAL");
        DocumentContentDescriptor descriptor = descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS");
        String documentIssue = "0A";

        for (Folder folder : folders) {

            switch(folder.getFolderName()) {

                case "Concept":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0101");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","OCD");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName, documentLongName, docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;

                case "Planning":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0102");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","SEMP");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName, documentLongName, docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;

                case "Specification":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0103");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","SYS");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName, documentLongName, docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;

                case "Design":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0104");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","SSDD");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName, documentLongName, docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;

                case "Integration":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0105");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","ICD");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName, documentLongName, docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;

                case "Validation":

                    wbs = wbsPackageRepository.findFirstByProjectAndWbsNumber(project, "0106");
                    identRoot = project.getNumericNumber() + wbs.getWbsCode().toUpperCase();
                    docType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("DOCUMENT_TYPE_ENTITY","VRS");
                    ident = identRoot + '-' + docType.getEntityTypeCode().toUpperCase() + "-" + wbs.getWbsNumber() + String.format("%04d", 1);
                    documentName = project.getProjectNumber() + " " + docType.getEntityTypeCode();
                    documentLongName = project.getProjectNumber() + " " + docType.getEntityTypeName();
                    documentRepository.save(new Document(ident, documentName,documentLongName , docType, descriptor, project, wbs, folder, infoClass, documentIssue));

                break;
            }
        }
        
        initClaims(project);
        initDocumentOwner(project, user);
        initDocumentSystemVariables(project);
    }
    
    private void initClaims(Project project) {
        
        ClaimType claimType = claimTypeRepository.findFirstByTypeName("DOCUMENT_OWNER");

        List<Document> documents = new ArrayList<>();
        documentRepository.findByProject(project).forEach(documents::add);
 
        for (Document document : documents) {

            Claim claim = claimRepository.save(new Claim(

                    claimType, 
                    document.getId(), 
                    claimType.getTypeDescription() + " - " + 
                    document.getDocumentName() + " (" + document.getIdentifier() + ")"));

            userClaimRepository.save(new UserClaim(project.getProjectManager(), claim));
        }
    }
    
    private void initDocumentOwner(Project project, User user) {
        
        List<Document> documents = new ArrayList<>();
        documentRepository.findByProject(project).forEach(documents::add); 
        
        for (Document document : documents) {
            
            document.setOwner(user);
            documentRepository.save(document);
        }
    }
    
    @Override
    public void initDocumentSystemVariables(Project project) {
        
        List<Document> documents = new ArrayList<>();
        documentRepository.findByProject(project).forEach(documents::add);
        
        for (Document document : documents) {

            String wbs = document.getWbs().getWbsCode();
            String uuidTemplate = "ID" + document.getProject().getNumericNumber() + "-" + wbs + "-" + document.getDocumentType().getEntityTypeCode() + "-";
            String reqIdTemplate = "R" + document.getProject().getNumericNumber() + "-" + wbs + "-";

            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_NUMERIC_DIGITS.name(), "5", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_NUMERIC_DIGITS.name(), "5", "DOCUMENT", document.getId()));

            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_TEMPLATE.name(), uuidTemplate, "DOCUMENT", document.getId()));

            List<EntityType> entityTypes = entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("ITEM_TYPE_ENTITY");

            for (EntityType type : entityTypes) {

                sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + type.getEntityTypeCode() + "-", "DOCUMENT", document.getId()));
            }
        }
    }

    @Override
    public List<Definition> addDefinition(DefinitionDao dao) {
        
        Definition def;
        Locale language = localeRepository.findByCode("en_GB");
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        
        if (!dao.getNewTerm().isEmpty()) {
            
            def = new Definition(dao.getNewTerm(), 
                                    dao.getNewTermDefinition(), 
                                    DefinitionTypes.valueOf(dao.getDefinitionType()), 
                                    language); 
            
            def = definitionRepository.save(def);
            
        } else {
            
            def = definitionRepository.findOne(dao.getDefinitionId());
        }
        
        List<Definition> definitions = document.getDefinitions();
        definitions.add(def);
        
        document.setDefinitions(new ArrayList(definitions)); 
        documentRepository.save(document);
        
        return definitions;
    }

    @Override
    public Document createBaseLine(BaseLineDao dao) {
        
        BaseLine baseLine = new BaseLine(entityTypeRepository.findOne(dao.getEntityTypeId()),
                                         dao.getBaseLineName(),
                                         dao.getBaseLineDescription());
        
        baseLine = baseLineRepository.save(baseLine);
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        document.setBaseLine(baseLine); 
        documentRepository.save(document);
        
        return document;
    }
}
