/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import org.zafritech.core.services.DocumentPDFService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.DocumentHistory;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentHistoryRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.constants.PdfConstants;

/**
 *
 * @author LukeS
 */
@Service
public class DocumentPDFServiceImpl implements DocumentPDFService {

    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserClaimRepository userClaimRepository;

    @Autowired
    private DocumentHistoryRepository historyRepository;
    
    @Override
    public void addMetadata(Document document, Long id) {
        
        org.zafritech.core.data.domain.Document doc = documentRepository.findOne(id);
        
        document.addTitle(doc.getDocumentName());
        document.addSubject(doc.getDocumentLongName());
        document.addAuthor(doc.getOwner().getFirstName() + " " + doc.getOwner().getLastName());
        document.addCreator(doc.getOwner().getFirstName() + " " + doc.getOwner().getLastName());
    }

    @Override
    public void addFrontPage(Document document, Long id) throws DocumentException {
        
        org.zafritech.core.data.domain.Document doc = documentRepository.findOne(id);
        
        // Add Space above Document Title
        Paragraph space = new Paragraph(" ", PdfConstants.NORMAL);
        addEmptyLine(space, 5);
        document.add(space);

        // Add Identifier Title
        Paragraph identifier = new Paragraph(doc.getIdentifier(), PdfConstants.SUBTITLE);
        identifier.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(identifier, 4);
        document.add(identifier);

        // Add Document Title
        Paragraph title = new Paragraph(doc.getDocumentType().getEntityTypeName(), PdfConstants.TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add Project Title
        Paragraph project = new Paragraph(doc.getProject().getProjectName(), PdfConstants.TITLE);
        project.setAlignment(Element.ALIGN_CENTER);
        document.add(project);
    }
    
    @Override
    public void addApprovalPage(Document document, Long id) throws DocumentException {
        
        org.zafritech.core.data.domain.Document doc = documentRepository.findOne(id);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PdfPCell cell;

        document.setMargins(PdfConstants.MARGIN_LEFT_DEFAULT, 
                       PdfConstants.MARGIN_RIGHT_DEFAULT, 
                       PdfConstants.MARGIN_TOP_DEFAULT, 
                       PdfConstants.MARGIN_BOTTOM_DEFAULT);
        document.newPage();

        Paragraph approvalTitle = new Paragraph();
        approvalTitle.add(new Chunk("Approval Sheet", PdfConstants.HEADER1));
        addEmptyLine(approvalTitle, 3);
        document.add(approvalTitle);

        float[] colWidths = {1, 2};
        PdfPTable table = new PdfPTable(colWidths);
        table.setTotalWidth(447);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();

        properties.put("Document Title:", doc.getDocumentLongName());
        properties.put("Document Reference:", doc.getIdentifier());
        properties.put("Revision:", doc.getDocumentIssue());
        properties.put("Date:", dateFormat.format(doc.getModifiedDate()));
        properties.put("Classification:", doc.getInfoClass().getClassName());
        properties.put("Filename Code:", doc.getUuId());
        properties.put("Synopsis:", "");
        properties.put("Keywords:", "");

        for (Map.Entry property : properties.entrySet()) {

            cell = new PdfPCell(new Phrase(property.getKey().toString(), PdfConstants.TABLE_CELL_BOLD));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(15);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(property.getValue().toString(), PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(15);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        document.add(table);

        // =================================================
        // Approval table
        // =================================================
        Paragraph spacerPara = new Paragraph();
        addEmptyLine(spacerPara, 3);
        document.add(spacerPara);

        String preparer = doc.getOwner().getFirstName() != null ? doc.getOwner().getFirstName() : "";
        preparer = doc.getOwner().getLastName() != null ? preparer + " " + doc.getOwner().getLastName() : preparer;
        String approver = "";

        ClaimType claimType = claimTypeRepository.findFirstByTypeName("DOCUMENT_APPROVER");
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, doc.getId());
        UserClaim userClaim = userClaimRepository.findFirstByClaim(claim);

        if (userClaim != null) {

            approver = userClaim.getUser().getFirstName() + " " + userClaim.getUser().getLastName();
        }

        float[] signaturesColWidths = {2, 3, 1, 1, 2};
        PdfPTable signTable = new PdfPTable(signaturesColWidths);
        signTable.setTotalWidth(447);
        signTable.setLockedWidth(true);
        signTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        LinkedHashMap<String, String> signatures = new LinkedHashMap<String, String>();

        signatures.put("Prepared By:", preparer);
        signatures.put("Approved By:", approver);
        signatures.put("Quality By:", "");

        for (Map.Entry approval : signatures.entrySet()) {

            // Preparer row
            cell = new PdfPCell(new Phrase(approval.getKey().toString(), PdfConstants.TABLE_CELL_BOLD));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(5);
            cell.setBorder(Rectangle.NO_BORDER);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(5);
            cell.setBorder(Rectangle.BOTTOM);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(5);
            cell.setBorder(Rectangle.NO_BORDER);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Date:", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(5);
            cell.setBorder(Rectangle.NO_BORDER);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(5);
            cell.setBorder(PdfPCell.BOTTOM);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingBottom(40);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(approval.getValue().toString(), PdfConstants.HEADER_LABEL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingBottom(40);
            cell.setBorder(Rectangle.NO_BORDER);
            signTable.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            cell.setPaddingBottom(40);
            cell.setBorder(Rectangle.NO_BORDER);
            signTable.addCell(cell);
        }

        document.add(signTable);
    }
    
    @Override
    public void addDistributionSheet(Document document, Long id) throws DocumentException {
        
        document.newPage();
        Paragraph distributionTitle = new Paragraph();
        distributionTitle.add(new Chunk("Distribution Lists", PdfConstants.HEADER1)); 
        addEmptyLine(distributionTitle, 4);
        document.add(distributionTitle);
        
        float[] distColWidths = {1, 6, 10};
        PdfPTable distTable = new PdfPTable(distColWidths);
        distTable.setTotalWidth(447);
        distTable.setLockedWidth(true);
        
        PdfPCell cell;
        Integer count = 1;
        
        cell = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(5);
        distTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Name/Title", PdfConstants.TABLE_HEADER));
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setPaddingBottom(5);
        distTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Address", PdfConstants.TABLE_HEADER));
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setPaddingBottom(5);
        distTable.addCell(cell);
        
        org.zafritech.core.data.domain.Document doc = documentRepository.findOne(id);
        
        for (User user : doc.getDistributions()) {
            
            cell = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setPaddingBottom(5);
            distTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(user.getFirstName() + " " + user.getLastName(), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            distTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(user.getContact().getCity() + ", " + user.getContact().getCountry().getCountryName().toUpperCase(), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            distTable.addCell(cell);
        }
        
        document.add(distTable);
    }

    @Override
    public void addChangeControlSheet(Document document, Long id) throws DocumentException {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        List<DocumentHistory> changeHistory = historyRepository.findByDocumentIdOrderByChangeDateAsc(id);
        
        document.newPage();
        Paragraph changeTitle = new Paragraph();
        changeTitle.add(new Chunk("Change Control", PdfConstants.HEADER1));
        addEmptyLine(changeTitle, 3);
        document.add(changeTitle);
        
        float[] refColWidths = {1, 3, 3, 6, 2, 2};
        PdfPTable refTable = new PdfPTable(refColWidths);
        refTable.setTotalWidth(447);
        refTable.setLockedWidth(true);

        // Table headers
        PdfPCell cell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        cell1.setBackgroundColor(new GrayColor(0.75f));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setPaddingBottom(5);
        refTable.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Date", PdfConstants.TABLE_HEADER));
        cell2.setBackgroundColor(new GrayColor(0.75f));
        cell2.setPaddingBottom(5);
        refTable.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase("ECP Number", PdfConstants.TABLE_HEADER));
        cell3.setBackgroundColor(new GrayColor(0.75f));
        cell3.setPaddingBottom(5);
        refTable.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Phrase("Change Summary", PdfConstants.TABLE_HEADER));
        cell4.setBackgroundColor(new GrayColor(0.75f));
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingBottom(5);
        refTable.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Phrase("Issue", PdfConstants.TABLE_HEADER));
        cell5.setBackgroundColor(new GrayColor(0.75f));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setPaddingBottom(5);
        refTable.addCell(cell5);

        PdfPCell cell6 = new PdfPCell(new Phrase("Pages", PdfConstants.TABLE_HEADER));
        cell6.setBackgroundColor(new GrayColor(0.75f));
        cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell6.setPaddingBottom(5);
        refTable.addCell(cell6);

        PdfPCell cell;
        Integer count = 1;
        
        for (DocumentHistory change : changeHistory) {
            
            cell = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(dateFormat.format(change.getChangeDate()), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(change.getEcpNumber(), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(change.getChangeSummary(), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(change.getRevision(), PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase("ALL", PdfConstants.TABLE_CELL)); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setPaddingBottom(5);
            refTable.addCell(cell);
        }

        document.add(refTable);
    }
 
    @Override
    public void addTableOfContents(Document document, List<AbstractMap.SimpleEntry<String, Integer>> entries) throws DocumentException {
        
        // ===================================================
        // Add Table of Contents START
        // ===================================================
        document.newPage();
        Paragraph tocTitle = new Paragraph();
        tocTitle.add(new Chunk("Table of Contents", PdfConstants.HEADER1));
        addEmptyLine(tocTitle, 1);
        document.add(tocTitle);

        Paragraph para;

        Chunk dottedLine = new Chunk(new DottedLineSeparator());

        for (AbstractMap.SimpleEntry<String, Integer> entry : entries) {

            para = new Paragraph(entry.getKey());
            para.add(dottedLine);
            para.add(String.valueOf(entry.getValue()));
            document.add(para);
        }
        // END Table of Contents
    }
    
    @Override
    public void addEmptyLine(Paragraph paragraph, Integer lines) {

        for (int i = 0; i < lines; i++) {

            paragraph.add(new Paragraph(" "));
        }
    }
}
