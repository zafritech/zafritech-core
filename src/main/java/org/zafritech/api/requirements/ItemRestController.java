/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.requirements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.data.dao.ReferenceDao;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentReference;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Reference;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.repositories.DocumentReferenceRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.SystemVariableRepository;
import org.zafritech.core.enums.ReferenceTypes;
import org.zafritech.core.services.FileIOService;
import org.zafritech.core.services.ReferenceService;
import org.zafritech.requirements.data.dao.DocumentTemplateIdsDao;
import org.zafritech.requirements.data.dao.DummyItemDao;
import org.zafritech.requirements.data.dao.ExcelItemsDao;
import org.zafritech.requirements.data.dao.ItemDao;
import org.zafritech.requirements.data.dao.ItemRefDao;
import org.zafritech.requirements.data.dao.ItemTreeDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemHistory;
import org.zafritech.requirements.data.domain.Template;
import org.zafritech.requirements.data.repositories.ItemHistoryRepository;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.data.repositories.TemplateRepository;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.services.DummyItemsService;
import org.zafritech.requirements.services.ItemService;

/**
 *
 * @author LukeS
 */
@RestController
public class ItemRestController {
    
    @Value("${zafritech.paths.images-dir}")
    private String images_dir;
    
    @Autowired
    private DocumentRepository documentRepository;
     
    @Autowired
    private DocumentReferenceRepository docReferenceRepository;
     
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ItemHistoryRepository historyRepository;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ReferenceService referenceService;
    
    @Autowired
    private TemplateRepository templateRepository;
    
    @Autowired
    private SystemVariableRepository variableRepository;
    
    @Autowired
    private DummyItemsService dummyItemsService;
    
    @Autowired
    private FileIOService fileIOService;
    
    @RequestMapping(value = "/api/requirements/document/items/item/{id}", method = RequestMethod.GET)
    public Item getItemById(@PathVariable(value = "id") Long itemId) {

        Item item = itemRepository.findOne(itemId);
        
        return item;
    }

    @RequestMapping(value = "/api/requirements/document/requirements/list/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getRequirementsByDocument(@PathVariable(value = "id") Long id) {

        List<Item> requirements = itemRepository.findByDocumentAndItemClass(documentRepository.findOne(id), ItemClass.REQUIREMENT.name());
        
        return new ResponseEntity<List<Item>>(requirements, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/list/view/{id}/{sectionId}", method = RequestMethod.GET)
    public ModelAndView listRequirementsForDocument(@PathVariable(value = "id") Long id,
                                                    @PathVariable(value = "sectionId") Long sectionId) {

        List<Item> items;
        Document document = documentRepository.findOne(id);
        
        if (sectionId > 0) {
            
            Item section = itemRepository.findOne(sectionId);
            items = itemService.fetchDocumentItemsForSection(document, section);
            
        } else {
        
            items = itemService.fetchDocumentItems(document); 
        }
        
        List<DocumentReference> applicable = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefTitleAsc(document, ReferenceTypes.REFERENCE_APPLICABLE);
        List<DocumentReference> referenced = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefTitleAsc(document, ReferenceTypes.REFERENCE_REFERENCED);
        
        ModelAndView modelView = new ModelAndView("views/items/document-items");
        modelView.addObject("document", document);
        modelView.addObject("items", items);
        modelView.addObject("applicables", applicable);
        modelView.addObject("referenced", referenced);
        
        return modelView;
    }
    
    @RequestMapping(value = "/api/requirements/document/items/item/details/{id}", method = RequestMethod.GET)
    public ModelAndView showRequirementsItemDetails(@PathVariable(value = "id") Long id) {
        
        Item item = itemRepository.findOne(id);
        Document document = documentRepository.findOne(item.getDocument().getId()); 
        List<ItemHistory> history = historyRepository.findAllByItemIdOrderByCreationDateDesc(id);
        
        ModelAndView modelView = new ModelAndView("views/items/item-details");
        modelView.addObject("document", document);
        modelView.addObject("item", item);
        modelView.addObject("history", history);
        
        return modelView;
    }
    
    @RequestMapping(value = "/api/requirements/document/item/types/list", method = RequestMethod.GET)
    public ResponseEntity<List<EntityType>> getItemTypes() {

        List<EntityType> itemTypes = entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("ITEM_TYPE_ENTITY");
        
        return new ResponseEntity<List<EntityType>>(itemTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/verification/methods", method = RequestMethod.GET)
    public ResponseEntity<List<EntityType>> getRerificationMethods() {

        List<EntityType> itemTypes = entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("VERIFICATION_VALIDATION_METHOD_TYPE_ENTITY");
        
        return new ResponseEntity<List<EntityType>>(itemTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/newitem/ref/dao/{id}", method = RequestMethod.GET)
    public ItemRefDao createFirstDocumentItem(@PathVariable(value = "id") Long documentId) {

        ItemRefDao createDao = itemService.getDaoForItemCreation(documentId);
        
        return createDao;
    }

    @RequestMapping(value = "/api/requirements/document/items/image/add", method = RequestMethod.POST)
    public ResponseEntity<?> addImageItem(@RequestParam("imageFile") MultipartFile upLoadFile,
                                          @RequestParam("documentId") Long documentId,
                                          @RequestParam("parentId") Long parentId,
                                          @RequestParam("imageCaption") String imageCaption,
                                          @RequestParam("itemLevel") Integer itemLevel) {
        
        if (upLoadFile.isEmpty()) {
            
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        
        String mimetype = upLoadFile.getContentType();
        if (!mimetype.startsWith("image/")) {
            
            return new ResponseEntity("The uploaded file is not an Image: " + upLoadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        Item imageItem = itemService.saveImageItem(upLoadFile, documentId, parentId, imageCaption, itemLevel);
        
        return new ResponseEntity("Successfully uploaded - Image name: " + imageItem.getItemValue(), new HttpHeaders(), HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/requirements/document/items/identifier/next", method = RequestMethod.GET)
    public ResponseEntity<String> getNextItemIentifier(@RequestParam(value = "id", required = true) Long id,
                                                       @RequestParam(value = "template", required = true) String template) {

        String reqIdentifier = itemService.getNextRequirementIdentifier(id, template);

        return new ResponseEntity<String>(reqIdentifier, HttpStatus.OK);
    }
   
    @RequestMapping(value = "/api/requirements/document/items/item/verification/method/update", method = RequestMethod.GET)
    public ResponseEntity<String> updateItemVerificationMethod(@RequestParam(value = "itemId", required = true) Long itemId,
                                                               @RequestParam(value = "entityTypeId", required = true) Long entityTypeId) {

        EntityType method = entityTypeRepository.findOne(entityTypeId);
        Item item = itemRepository.findOne(itemId);
        item.setVerificationMethod(method);
        itemRepository.save(item);
        

        return new ResponseEntity<String>(method.getEntityTypeName(), HttpStatus.OK);
    }
           
    @RequestMapping(value = "/api/requirements/document/items/item/template", method = RequestMethod.GET)
    public ResponseEntity<String> getItemIentifierTemplate(@RequestParam(value = "id", required = true) Long id,
                                                           @RequestParam(value = "typeId", required = true) Long typeId) {

        String typeCode = entityTypeRepository.findOne(typeId).getEntityTypeCode();
        List<SystemVariable> variables = variableRepository.findByOwnerIdAndOwnerTypeAndVariableName(id, "DOCUMENT", "REQUIREMENT_ID_TEMPLATE");
        String identTemplate = "";
        
        for (SystemVariable variable : variables) {
            
            String value = variable.getVariableValue();
            
            if (value.lastIndexOf(typeCode) > -1) {
                
                identTemplate = variable.getVariableValue();
            }
        }

        return new ResponseEntity<String>(identTemplate, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/api/requirements/document/items/item/save", method = RequestMethod.POST)
    public ResponseEntity<Item> newRquirementsSaveItem(@RequestBody ItemDao itemDao) {
        
        Item item = itemService.saveRquirementItem(itemDao);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/api/requirements/document/items/dummy/items/save", method = RequestMethod.POST)
    public ResponseEntity<List<Item>> saveDummyRequirementItems(@RequestBody DummyItemDao dummyDao) {
        
        List<Item> items = dummyItemsService.createDummyItems(dummyDao); 

        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/edit/save", method = RequestMethod.POST)
    public ResponseEntity<Item> saveEditedItem(@RequestBody ItemDao itemDao) {
        
        Item item = itemService.saveEditedItemDao(itemDao);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/items/item/move", method = RequestMethod.GET)
    public ResponseEntity<Item> moveItem(@RequestParam(value = "id", required = true) Long id,
                                         @RequestParam(value = "direction", required = true) String direction) {
        
        itemService.moveItemUpOrDown(id, direction); 
        Item item = itemRepository.findOne(id);
        
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/items/item/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable(value = "id") Long id) {

        Item item = itemRepository.findOne(id);
        List<Item> children = itemRepository.findByParentOrderBySortIndexAsc(item);
        
        if (children.isEmpty()) {
        
            // IMAGE: remove physical image from disk as well
            if (item.getItemClass().equals(ItemClass.IMAGE.name())) {
                
                try {
                
                    Path path = Paths.get(images_dir + item.getItemValue());
                    Files.deleteIfExists(path);
                    
                } catch (IOException ex) {
                    
                    Logger.getLogger(ItemRestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Item parent = item.getParent();
            itemRepository.delete(item);
            
            if (parent != null) {
                
                itemService.reNumberChildItems(parent); 
            }
            
        } else {
            
            return new ResponseEntity<Item>(item, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/import/items/from/template", method = POST)
    public ResponseEntity<Document> importDocumentFromTemplate(@RequestBody DocumentTemplateIdsDao dao) {
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        Template template = templateRepository.findOne(dao.getTemplateId());
        
        Document doc = itemService.importTemplateToDocument(document, template);
                
        return new ResponseEntity<Document>(doc, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/tree/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ItemTreeDao>> getDocumentItemTree(@PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id);
        List<ItemTreeDao> headersTree = itemService.getTableOfContents(document); 
        
        return new ResponseEntity<List<ItemTreeDao>>(headersTree, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/opendocs/tree/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ItemTreeDao>> getOpenDocumentsItemTree(@PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id);
        List<ItemTreeDao> headersTree = itemService.getOpenDocumentTitlesTreeExcluding(document);  
        
        return new ResponseEntity<List<ItemTreeDao>>(headersTree, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/opendocs/tree/all", method = RequestMethod.GET)
    public ResponseEntity<List<ItemTreeDao>> getOpenDocumentsItemTreeAll() {
        
        List<ItemTreeDao> headersTree = itemService.getOpenDocumentTitlesTreeAll();  
        
        return new ResponseEntity<List<ItemTreeDao>>(headersTree, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/requirements/document/reference/add", method = RequestMethod.POST)
    public ResponseEntity<Reference> addDocumentReference(@RequestBody ReferenceDao refDao) {
        
        Reference reference = referenceService.addDocumentReference(refDao);

        return new ResponseEntity<Reference>(reference, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/requirements/baseline/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<Document> baseLineRequirementsItems(@PathVariable(value = "id") Long id) {
        
        Document document = itemService.baseLineRequirementsItems(documentRepository.findOne(id));

        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/import/items/from/excel", method = RequestMethod.POST)
    public ResponseEntity<?> importItemsFromExcel(ExcelItemsDao excelDao) throws IOException, ParseException {
        
        if (excelDao.getExcelFile().isEmpty()) {
            
            return new ResponseEntity("Please select a an excel file!", HttpStatus.BAD_REQUEST);
        }
        
        String fileName = excelDao.getExcelFile().getOriginalFilename();
        
        if (!FilenameUtils.isExtension(fileName,"xls") && !FilenameUtils.isExtension(fileName,"xlsx")) {
            
            return new ResponseEntity("The selected file is not a MS Excel file.", HttpStatus.BAD_REQUEST);
        }
        
        List<MultipartFile> files = new ArrayList<>();
        files.add(excelDao.getExcelFile());
        List<String> filePaths = fileIOService.saveUploadedFiles(files);
        String filePath = filePaths.get(0);
        
        Integer itemCount = itemService.importFromExcelFile(filePath, excelDao.getDocumentId());
        
        if (itemCount < 0) {
            
            return new ResponseEntity("There was an error process the Excel file. Please check that the file meets the required format specification.", HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity("Successfully imported " + itemCount + " requirements from Excel", new HttpHeaders(), HttpStatus.OK);
    }
}
