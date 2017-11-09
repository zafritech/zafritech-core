/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zafritech.core.data.domain.BaseLine;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.SystemVariableRepository;
import org.zafritech.core.enums.SystemVariableTypes;
import org.zafritech.core.services.ExcelService;
import org.zafritech.core.services.FileIOService;
import org.zafritech.requirements.data.converters.DaoToRefItemConverter;
import org.zafritech.requirements.data.dao.ItemDao;
import org.zafritech.requirements.data.dao.ItemRefDao;
import org.zafritech.requirements.data.dao.ItemTreeDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.Link;
import org.zafritech.requirements.data.domain.Template;
import org.zafritech.requirements.data.domain.TemplateItem;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.data.repositories.TemplateItemRepository;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.enums.MediaType;
import org.zafritech.requirements.services.ItemService;
import org.zafritech.core.services.UserSessionService;
import org.zafritech.requirements.data.domain.ItemHistory;
import org.zafritech.requirements.data.repositories.ItemHistoryRepository;
import org.zafritech.requirements.enums.ItemStatus;

/**
 *
 * @author LukeS
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${zafritech.paths.images-dir}")
    private String images_dir;
    
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemHistoryRepository historyRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private SystemVariableRepository sysvarRepository;
    
    @Autowired
    private TemplateItemRepository templateItemRepository;
        
    @Autowired
    private FileIOService fileIOService;
    
    @Autowired
    private UserSessionService stateService;
    
    @Autowired
    private ExcelService excelService;
    
    @Autowired
    private SystemVariableRepository variableRepository;
    
    @Override
    public ItemRefDao getDaoForItemCreation(Long id) {
        
        
        DaoToRefItemConverter createConverter = new DaoToRefItemConverter();
        Item item = new Item();
        item.setDocument(documentRepository.findOne(id)); 
        ItemRefDao createDao = createConverter.convert(item);
        
        createDao.setItemTypes(entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("ITEM_TYPE_ENTITY"));
        createDao.setIdentPrefices(sysvarRepository.findByOwnerIdAndOwnerTypeAndVariableNameOrderByVariableValue(
                                                    id, 
                                                    "DOCUMENT", 
                                                    SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name()));
        
        return createDao;
    }

    @Override
    public void updateItemHistory(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLinksChanged(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetLinkChanged(Item item, Link link) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int incrCommentCount(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNextSystemIdentifier(Long id) {

        String template = getSystemIDTemplate(id, "DOCUMENT", SystemVariableTypes.ITEM_UUID_TEMPLATE.name());
        List<SystemVariable> digitsList = sysvarRepository.findByOwnerIdAndOwnerTypeAndVariableName(id, "DOCUMENT", SystemVariableTypes.ITEM_UUID_NUMERIC_DIGITS.name());
        String digits = digitsList.get(0).getVariableValue();
        String format = "%0" + digits + "d";

        String regex = "(\\d+$)";
        Pattern pattern = Pattern.compile(regex);

        List<Item> items = itemRepository.findByDocumentIdOrderBySortIndexAsc(id);
        List<String> list = new ArrayList<String>();

        for (Item item : items) {

            Matcher matcher = pattern.matcher(item.getSystemId());

            if (matcher.find()) {

                String listItem = String.format(format, Integer.parseInt(matcher.group(1)));

                list.add(listItem);
            }
        }

        list = list.stream().sorted().collect(Collectors.toList());

        if (!list.isEmpty()) {

            return template + String.format(format, Integer.parseInt(list.get(list.size() - 1)) + 1);

        } else {

            return template + String.format(format, 1);
        }
    }

    @Override
    public String getNextRequirementIdentifier(Long id, String template) {

        List<SystemVariable> digitsList = sysvarRepository.findByOwnerIdAndOwnerTypeAndVariableName(id, "DOCUMENT", SystemVariableTypes.REQUIREMENT_ID_NUMERIC_DIGITS.name());
        String digits = digitsList.get(0).getVariableValue();
        String format = "%0" + digits + "d";

        String regex = "(\\d+$)";
        Pattern pattern = Pattern.compile(regex);

        List<Item> items = itemRepository.findByDocumentIdOrderBySortIndexAsc(id);
        List<String> list = new ArrayList<String>();

        for (Item item : items) {

            if (item.getIdentifier() != null) {
                
                Matcher matcher = pattern.matcher(item.getIdentifier());

                if (matcher.find()) {

                    String listItem = String.format(format, Integer.parseInt(matcher.group(1)));

                    list.add(listItem);
                }
            }
        }

        list = list.stream().sorted().collect(Collectors.toList());

        if (!list.isEmpty()) {

            return template + String.format(format, Integer.parseInt(list.get(list.size() - 1)) + 1);

        } else {

            return template + String.format(format, 1);
        }
    }

    @Override
    public Item saveItem(Item item) {
        
        Item saved = itemRepository.save(item);
        updateDocumentLastUpdateTime(saved.getDocument().getId());

        return saved;
    }

    @Override
    public Item saveImageItem(MultipartFile upLoadedFile, Long documentId, Long parentId, String imageCaption, Integer itemLevel) {
            
        Item imageItem;
            
        try {

            Document document = documentRepository.findOne(documentId);
            Item parent = itemRepository.findOne(parentId);

            String fileExtension = FilenameUtils.getExtension(upLoadedFile.getOriginalFilename());
            String imageRelPath = "documents/doc_" + document.getUuId() + "/img_" + UUID.randomUUID().toString() + "." + fileExtension;
            String imageFullPath = images_dir + imageRelPath;

            // Upload and move file
            List<String> files = fileIOService.saveUploadedFiles(Arrays.asList(upLoadedFile));
            FileUtils.moveFile(FileUtils.getFile(files.get(0)), FileUtils.getFile(imageFullPath)); 

            imageItem  =    new Item(getNextSystemIdentifier(documentId),
                                     imageRelPath,
                                     null, 
                                     document,
                                     parent);

            imageItem.setItemClass(ItemClass.IMAGE.name()); 
            imageItem.setItemLevel(itemLevel);
            imageItem.setMediaType(MediaType.valueOf(fileExtension.toUpperCase())); 
            if (!imageCaption.isEmpty()) { imageItem.setItemCaption(imageCaption); }
            
            // Get item's sort index
            Item lastChild;
            
            if (parent != null) {

                lastChild = itemRepository.findFirstByParentOrderBySortIndexDesc(parent);

            } else {

                lastChild = itemRepository.findFirstByDocumentAndItemLevelOrderBySortIndexDesc(document, 1); 
            }

            Integer index = lastChild != null ? lastChild.getSortIndex() + 1 : 0;
            imageItem.setSortIndex(index); 

            imageItem = itemRepository.save(imageItem);
            
        } catch (IOException e) {
            
            return null;
        }

        return imageItem;
    }
    
    @Override
    public Item saveRquirementItem(ItemDao itemDao) {
        
        String itemValue = itemDao.getItemValue().replace("<p><br></p>", "");
        
        Document document = documentRepository.findOne(itemDao.getDocumentId());
        Integer index;
        
        Item parent = itemDao.getParentId() != null ? itemRepository.findOne(itemDao.getParentId()) : null;
        
        Item item = new Item(getNextSystemIdentifier(itemDao.getDocumentId()),
                             itemDao.getItemClass().equalsIgnoreCase(ItemClass.REQUIREMENT.name()) ? itemDao.getIdentifier() : null,
                             itemValue,
                             itemDao.getItemClass().equalsIgnoreCase(ItemClass.REQUIREMENT.name()) ? entityTypeRepository.findOne(itemDao.getItemTypeId()) : null, 
                             itemDao.getMediaType(),
                             document,
                             parent);
        
        item.setItemClass(itemDao.getItemClass()); 
        item.setItemLevel(itemDao.getItemLevel()); 
        
        // Get item's sort index
        Item lastChild;
        if (parent != null) {
            
            lastChild = itemRepository.findFirstByParentOrderBySortIndexDesc(parent);
            
        } else {
            
            lastChild = itemRepository.findFirstByDocumentAndItemLevelOrderBySortIndexDesc(document, 1); 
        }
        
        index = lastChild != null ? lastChild.getSortIndex() + 1 : 0;
        item.setSortIndex(index); 
        
        item = itemRepository.save(item);
        
        if (item.getItemClass().equalsIgnoreCase(ItemClass.HEADER.name())) {
            
            updateHeaderItemNumbers(document);
        }
                    
        return item;
    }
    
    @Override
    public Item saveEditedItemDao(ItemDao itemDao) {
        
        Item item = itemRepository.findOne(itemDao.getId());
        String oldItemValue = item.getItemValue();
        String newItemValue = itemDao.getItemValue().replace("<p><br></p>", "");
        
        if (!itemDao.getItemClass().equalsIgnoreCase("HEADER")) {
            
            item.setItemValue(newItemValue); 
            item.setMediaType(itemDao.getMediaType()); 
        }
        
        if (itemDao.getItemClass().equalsIgnoreCase("REQUIREMENT")) {
            
            if (item.getBaseLine() != null) {
                
                ItemHistory history = new ItemHistory(item, item.getSystemId(), oldItemValue, item.getBaseLine(), item.getItemVersion());
                historyRepository.save(history);
                
                item.setBaseLine(null);
                item.setItemStatus(ItemStatus.ITEM_STATUS_CHANGED); 
            }
            
            item.setItemValue(newItemValue); 
            item.setIdentifier(itemDao.getIdentifier());
            item.setItemType(entityTypeRepository.findOne(itemDao.getItemTypeId())); 
        }
        
        return itemRepository.save(item); 
    }
  
    @Override
    public void moveItemUpOrDown(Long id, String direction) {
        
        Integer newIndex = 0;
        Item item = itemRepository.findOne(id);
        List<Item> siblings;
        
        if (direction.equalsIgnoreCase("UP")) {
            
            newIndex = item.getSortIndex() > 0 ? item.getSortIndex() - 1 : 0;
            
        } else if (direction.equalsIgnoreCase("DOWN")) {
            
            newIndex = item.getSortIndex() + 1;
        }
        
        if (item.getParent() != null) {     // Level > 1
            
            Item parent = item.getParent();
            siblings = itemRepository.findByParentOrderBySortIndexAsc(parent);
            
        } else {                            // Level == 1
            
            siblings = itemRepository.findByDocumentAndItemLevelOrderBySortIndexAsc(item.getDocument(), 1);
        }

        for (Item sibling : siblings) {

            if (sibling.getSortIndex() == newIndex) {

                sibling.setSortIndex(item.getSortIndex());
                itemRepository.save(sibling);

                item.setSortIndex(newIndex);
                itemRepository.save(item);
                
                updateHeaderItemNumbers(item.getDocument());
            }
        }
    }
  
    @Override
    public void reNumberChildItems(Item parent) {
        
        Integer index = 0;
        List<Item> items = itemRepository.findByParentOrderBySortIndexAsc(parent);
        
        for (Item item : items) {
            
            item.setSortIndex(index++);
            itemRepository.save(item);
        }
        
        updateHeaderItemNumbers(parent.getDocument());
    }
    
    @Override
    public List<Item> fetchDocumentItems(Document document) {
        
        List<Item> items = new ArrayList<>();
        List<Item> firstLevelItems = itemRepository.findByDocumentAndItemLevelOrderBySortIndexAsc(document, 1);
        
        for(Item item : firstLevelItems) {

            items.add(item);
            getItemChildren(item).forEach(items::add); 
        }
        
        return items;
    }
  
    @Override
    public List<Item> fetchDocumentItemsForSection(Document document, Item section) {
        
        List<Item> items = new ArrayList<>();
        items.add(section);
        
        List<Item> children = itemRepository.findByParentOrderBySortIndexAsc(section);
        
        for(Item item : children) {

            items.add(item);
            getItemChildren(item).forEach(items::add); 
        }
        
        return items;
    }
    
    @Override
    public List<ItemTreeDao> getTableOfContents(Document document) {
        
        List<ItemTreeDao> headersTree = new ArrayList<>();
        List<Item> headers = itemRepository.findByDocumentAndItemClassOrderBySortIndexAsc(document, ItemClass.HEADER.name());
       
        ItemTreeDao toc = new ItemTreeDao(0L, null, document.getIdentifier(), true, true, true, 0L); 
        toc.setIcon("/images/icons/write-icon.png");
        headersTree.add(toc);
        
        if (headers.isEmpty()) { headersTree.add(new ItemTreeDao(1L, 0L, "This document is empty.", false, false, true, 0L)); } 
        
        for (Item item : headers) {
            
            ItemTreeDao treeDao = new ItemTreeDao(item.getId(),
                                                  item.getParent() != null ? item.getParent().getId() : 0L,
                                                  item.getItemNumber() + " " + item.getItemValue(),
                                                  true,
                                                  true,
                                                  true,
                                                  item.getId());
             
//            treeDao.setIcon("/images/icons/file-icon.png");
            
            headersTree.add(treeDao);
        }
        
        return headersTree;
    }
   
    @Override
    public List<ItemTreeDao> getOpenDocumentTitlesTreeAll() {
        
        List<ItemTreeDao> headersTree = new ArrayList<>();
        
        List<Document> documents = stateService.getOpenDocuments();
        
        for (Document document : documents) {

            ItemTreeDao title = new ItemTreeDao(document.getId(), null, document.getIdentifier(), true, true, true, "/images/icons/page-icon.png", document.getId());
            headersTree.add(title);
        }
        
        return headersTree;
    }
    
    @Override
    public List<ItemTreeDao> getOpenDocumentTitlesTreeExcluding(Document openDocument) {
        
        List<ItemTreeDao> headersTree = new ArrayList<>();
        
        List<Document> documents = stateService.getOpenDocuments();
        
        for (Document document : documents) {
            
            if (document != openDocument) {
                
                ItemTreeDao title = new ItemTreeDao(document.getId(), null, document.getIdentifier(), true, true, true, "/images/icons/title-icon.png", document.getId());
                headersTree.add(title);
            }
        }
        
        return headersTree;
    }
    
    @Override
    public Document importTemplateToDocument(Document document, Template template) {
        
        List<TemplateItem> firstLevelTemplateItems = templateItemRepository.findByTemplateAndItemLevelOrderBySortIndexAsc(template, 1);
        
        for (TemplateItem templateItem : firstLevelTemplateItems) {

            Item item = saveTemplateItem(document, templateItem, null);
            saveTemplateItemChildren(document, templateItem, item);
        }

        return document;
    }

    @Override
    public Document baseLineRequirementsItems(Document document) {
        
        BaseLine baseLine = document.getBaseLine();
        
        if (baseLine != null) {

            List<Item> items = itemRepository.findByDocumentId(document.getId());

            for (Item item : items) {

                item.setBaseLine(baseLine);
                item.setItemVersion(item.getItemVersion() + 1); 
                
                if (item.getItemClass().equals(ItemClass.REQUIREMENT.name())) { 
                    
                    item.setItemStatus(ItemStatus.ITEM_STATUS_BASELINED); 
                }
                
                itemRepository.save(item);
            }
        }
        
        return document;
    }
   
    @Override
    public Integer importFromExcelFile(String filePath, Long documentId) {
        
        Item parent = null;
        Integer headerSortIndex = 0;
        Integer childSortIndex = 0;
        Integer itemCount = -1;
        
        EntityType entityType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("ITEM_TYPE_ENTITY", "UNC");
        
        try {
            
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = excelService.getExcelWorkbook(inputStream, filePath);
            Sheet worksheet = workbook.getSheetAt(0);
            
            int i = 1;      // Skip header row, i = 0
            
            while(i <= worksheet.getLastRowNum()) {
                
                Row row = worksheet.getRow(i++);
                
                String identifier = getNextRequirementIdentifier(documentId, getItemIentifierTemplate(documentId, entityType));
                
                String itemClass = (String)excelService.getExcelCellValue(row.getCell(3));
                Integer level = (int) (double)excelService.getExcelCellValue(row.getCell(2));
                
                ItemDao itemDao = new ItemDao();
                
                if (parent != null && parent.getItemLevel() >= level) {
                    
                    parent = parent.getParent();
                }
                
                itemDao.setDocumentId(documentId); 
                itemDao.setItemClass(itemClass);
                itemDao.setIdentifier(identifier); 
                itemDao.setItemValue((String)excelService.getExcelCellValue(row.getCell(1))); 
                itemDao.setItemLevel(level); 
                itemDao.setMediaType(MediaType.TEXT); 
                itemDao.setItemTypeId(entityType.getId());  
                itemDao.setParentId(parent != null ? parent.getId() : null); 
                itemDao.setSortIndex((itemClass.equalsIgnoreCase(ItemClass.HEADER.name())) ? headerSortIndex++ : childSortIndex++); 
                
                Item item = saveRquirementItem(itemDao);
                itemCount++;
                
                if (itemClass.equalsIgnoreCase(ItemClass.HEADER.name())) {
                    
                    parent = item;
                    childSortIndex = 0;
                }
            }
            
        } catch (IOException e) {
            
            
        }
        
        return itemCount;
    }
     
    /*********************************************************************************************************************
    * Private methods beyond this point
    **********************************************************************************************************************/
    
    private void saveTemplateItemChildren(Document document, TemplateItem templateItem, Item parent) {
        
        List<TemplateItem> templateItems = templateItemRepository.findByTemplateAndParentSysIdOrderBySortIndexAsc(templateItem.getTemplate(), templateItem.getSystemId());
        
        for (TemplateItem childTemplateItem : templateItems) {
            
            Item item = saveTemplateItem(document, childTemplateItem, parent);
            saveTemplateItemChildren(document, childTemplateItem, item);
        }
    }
    
    private Item saveTemplateItem(Document document, TemplateItem templateItem, Item parent) {

        Item item = new Item(getNextSystemIdentifier(document.getId()),
                             templateItem.getItemValue(),
                             templateItem.getItemType(),
                             document,
                             parent);
        
        item.setItemNumber(templateItem.getItemNumber());
        item.setItemClass(templateItem.getItemClass());
        item.setItemLevel(templateItem.getItemLevel());
        item.setMediaType(templateItem.getMediaType());
        item.setSortIndex(templateItem.getSortIndex()); 
        
        item = itemRepository.save(item);
        
        return item;
    }
    
    private List<Item> getItemChildren(Item item) {
        
        List<Item> children = new ArrayList<>();
        List<Item> childItems = itemRepository.findByParentOrderBySortIndexAsc(item); 
        
        for (Item child : childItems) {

            children.add(child);
            getItemChildren(child).forEach(children::add); 
        }
        
        return children;
    }
  
    private String getSystemIDTemplate(Long id, String ownerType, String name) {

        List<SystemVariable> sysVar = sysvarRepository.findByOwnerIdAndOwnerTypeAndVariableName(id, ownerType, name);

        return sysVar.get(0).getVariableValue();
    }

    private String getItemIentifierTemplate(Long documentId, EntityType entityType) {
        
        String identTemplate = "";
        List<SystemVariable> variables = variableRepository.findByOwnerIdAndOwnerTypeAndVariableName(documentId, "DOCUMENT", "REQUIREMENT_ID_TEMPLATE");
        
        for (SystemVariable variable : variables) {
            
            String value = variable.getVariableValue();
            
            if (value.lastIndexOf(entityType.getEntityTypeCode()) > -1) {
                
                identTemplate = variable.getVariableValue();
            }
        }
        
        return identTemplate;
    }

    private void updateHeaderItemNumbers(Document document) {
        
        Integer number = 1;
        ItemClass itemClass = ItemClass.HEADER;
        List<Item> firstLevelHeaders = itemRepository.findByDocumentAndItemLevelAndItemClassOrderBySortIndexAsc(document, 1, itemClass.name());
        
        for (Item item : firstLevelHeaders) {
            
            item.setItemNumber(number.toString()); 
            updateChildHeadersItemNummbers(item, number.toString());
            itemRepository.save(item);
            
            number++;
        }        
    }
    
    private void updateChildHeadersItemNummbers(Item item, String parentNumber) {
        
        Integer number = 1;
        ItemClass itemClass = ItemClass.HEADER;
        List<Item> childrenHeaders = itemRepository.findByParentAndItemClassOrderBySortIndexAsc(item, itemClass.name());
        
        for (Item child : childrenHeaders) {
            
            String childNumber = parentNumber + "." + number.toString();
            child.setItemNumber(childNumber);
            itemRepository.save(child);
            
            updateChildHeadersItemNummbers(child, childNumber);
            number++;
        }
    }
  
    private void updateDocumentLastUpdateTime(Long id) {

        Document doc = documentRepository.findOne(id);

        doc.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        documentRepository.save(doc);
    }
}
