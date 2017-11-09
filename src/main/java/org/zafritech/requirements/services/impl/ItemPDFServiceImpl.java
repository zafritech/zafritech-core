/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentReference;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentReferenceRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.enums.DefinitionTypes;
import org.zafritech.core.enums.ReferenceTypes;
import org.zafritech.constants.PdfConstants;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.services.ItemService;
import org.zafritech.core.services.DocumentPDFService;
import org.zafritech.requirements.services.ItemPDFService;

/**
 *
 * @author LukeS
 */
@Service
public class ItemPDFServiceImpl implements ItemPDFService {

    @Value("${zafritech.paths.images-dir}")
    private String images_dir;
    
    @Autowired
    private ItemService itemService;

    @Autowired
    private DocumentReferenceRepository docReferenceRepository;

    @Autowired
    private ClaimTypeRepository claimTypeRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserClaimRepository userClaimRepository;

    @Autowired
    private DocumentPDFService documentPdfService;
    
    @Override
    public byte[] getItemsPDFDocument(Document document) throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document(PageSize.A4,
                                                                        PdfConstants.MARGIN_LEFT_DEFAULT,
                                                                        PdfConstants.MARGIN_RIGHT_DEFAULT,
                                                                        PdfConstants.MARGIN_TOP_FIRST_PAGE,
                                                                        PdfConstants.MARGIN_BOTTOM_DEFAULT);
        
        PdfWriter writer = PdfWriter.getInstance(doc, os);

        // Document Header Event
        HeaderTable headerEvent = new HeaderTable(document);
        writer.setPageEvent(headerEvent);

        // Document Footer Event
        FooterTable footerEvent = new FooterTable(document);
        writer.setPageEvent(footerEvent);

        // TOC Footer Event
        TableOfContents tocEvent = new TableOfContents();
        writer.setPageEvent(tocEvent);

        doc.open();

        // Add Metadata
        documentPdfService.addMetadata(doc, document.getId());

        // Add Document Front Page
        documentPdfService.addFrontPage(doc, document.getId());

        // Add Approval Sheet
        documentPdfService.addApprovalPage(doc, document.getId());

        // Add Distribution Sheet
        documentPdfService.addDistributionSheet(doc, document.getId());

        // Add Change Control Sheet
        documentPdfService.addChangeControlSheet(doc, document.getId());

        // Add Table of Contents START
        List<SimpleEntry<String, Integer>> entries = tocEvent.getTOC();
        documentPdfService.addTableOfContents(doc, entries);

        // Add Document Body
        addContent(doc, document);

        // Close PDF document
        doc.close();

        // Stream out PDF as byte array
        byte[] pdfAsBytes = os.toByteArray();

        return pdfAsBytes;
    }

    private void addContent(com.itextpdf.text.Document pdf, Document document) throws DocumentException, BadElementException, IOException {

        pdf.newPage();
        pdf.setMargins(PdfConstants.MARGIN_LEFT_DEFAULT, 
                       PdfConstants.MARGIN_RIGHT_DEFAULT, 
                       PdfConstants.MARGIN_TOP_DEFAULT, 
                       PdfConstants.MARGIN_BOTTOM_DEFAULT);

        List<Item> items = itemService.fetchDocumentItems(document);

        if (!items.isEmpty()) {

            for (Item item : items) {

                switch (item.getItemClass()) {

                    case "HEADER":

                        switch (item.getItemLevel()) {

                            case 1:

                                pdf.newPage();
                                Paragraph header1 = new Paragraph();
                                header1.setTabSettings(new TabSettings(PdfConstants.TEXT_INDENT_LEFT));
                                header1.add(new Chunk(item.getItemNumber(), PdfConstants.HEADER1));
                                header1.add(Chunk.TABBING);
                                header1.add(new Chunk(item.getItemValue(), PdfConstants.HEADER1));
                                documentPdfService.addEmptyLine(header1, 1);
                                pdf.add(header1);

                                break;

                            case 2:

                                Paragraph header2 = new Paragraph();
                                header2.add(new Chunk(item.getItemNumber(), PdfConstants.HEADER2));
                                header2.setTabSettings(new TabSettings(PdfConstants.TEXT_INDENT_LEFT));
                                header2.add(Chunk.TABBING);
                                header2.add(new Chunk(item.getItemValue(), PdfConstants.HEADER2));
                                documentPdfService.addEmptyLine(header2, 1);
                                pdf.add(header2);

                                break;

                            case 3:

                                Paragraph header3 = new Paragraph();
                                header3.add(new Chunk(item.getItemNumber(), PdfConstants.HEADER3));
                                header3.setTabSettings(new TabSettings(PdfConstants.TEXT_INDENT_LEFT));
                                header3.add(Chunk.TABBING);
                                header3.add(new Chunk(item.getItemValue(), PdfConstants.HEADER3));
                                documentPdfService.addEmptyLine(header3, 1);
                                pdf.add(header3);

                                break;

                            case 4:

                                Paragraph header4 = new Paragraph();
                                header4.add(new Chunk(item.getItemNumber(), PdfConstants.HEADER4));
                                header4.setTabSettings(new TabSettings(PdfConstants.TEXT_INDENT_LEFT));
                                header4.add(Chunk.TABBING);
                                header4.add(new Chunk(item.getItemValue(), PdfConstants.HEADER4));
                                documentPdfService.addEmptyLine(header4, 1);
                                pdf.add(header4);

                                break;

                            default:
                        }

                        break;

                    case "PROSE":

                        Paragraph prose = new Paragraph();
                        prose.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
                        prose.add(new Paragraph(item.getItemValue().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PdfConstants.NORMAL));
                        documentPdfService.addEmptyLine(prose, 1);
                        pdf.add(prose);

                        break;

                    case "REQUIREMENT":

                        Paragraph requirement = new Paragraph();
                        requirement.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
                        requirement.add(new Chunk(item.getIdentifier() + ": ", PdfConstants.INDENTIFIER));
                        requirement.add(new Chunk(item.getItemValue().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PdfConstants.NORMAL));
                        documentPdfService.addEmptyLine(requirement, 1);
                        pdf.add(requirement);

                        break;

                    case "METADATA":

                        switch (item.getMediaType()) {

                            case REFERENCES:

                                addReferences(document, item, pdf);

                                break;

                            case ACRONYMS_ABBREVIATIONS:

                                addAcronymsAbbreviations(document, pdf);

                                break;

                            case ABBREVIATIONS:

                                addAbbreviations(document, pdf);

                                break;

                            case ACRONYMS:

                                addAcronyms(document, pdf);

                                break;

                            case DEFINITIONS:

                                addDefinitions(document, pdf);

                                break;
                        }

                        break;

                    case "IMAGE":
                        
                        String path = images_dir + item.getItemValue();
                        Image image = Image.getInstance(path);
                        
                        float usablePageWidth = pdf.getPageSize().getWidth() - (PdfConstants.MARGIN_LEFT_DEFAULT + PdfConstants.MARGIN_RIGHT_DEFAULT + PdfConstants.TEXT_INDENT_LEFT);
                        float imageWidth = image.getPlainWidth();
                        
                        if (imageWidth > usablePageWidth) {
                            
                            float imageReducedPercentageWidth = usablePageWidth / imageWidth * 100;
                            image.scalePercent(imageReducedPercentageWidth); 
                            image.setAlignment(Image.RIGHT); 
                            pdf.add(image);
                            
                        } else {
                            
                            image.setAlignment(Image.MIDDLE); 
                            image.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
                        }
                        
                        Paragraph imagePara = new Paragraph();
                        documentPdfService.addEmptyLine(imagePara, 1);
                        pdf.add(imagePara);
                        
                        break;
                        
                    default:

                }
            }
        }
    }

    private void addReferences(Document document, Item item, com.itextpdf.text.Document pdf) throws DocumentException {

        List<DocumentReference> references = new ArrayList<>();

        if (item.getItemValue().equalsIgnoreCase(ReferenceTypes.REFERENCE_APPLICABLE.name())) {

            references = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefTitleAsc(document, ReferenceTypes.REFERENCE_APPLICABLE);

        } else if (item.getItemValue().equalsIgnoreCase(ReferenceTypes.REFERENCE_REFERENCED.name())) {

            references = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefTitleAsc(document, ReferenceTypes.REFERENCE_REFERENCED);
        }

        float[] refColWidths = {1, 6, 12, 2};
        PdfPTable refTable = new PdfPTable(refColWidths);
        refTable.setWidthPercentage(100);

        // Table headers
        PdfPCell cell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        cell1.setBackgroundColor(new GrayColor(0.75f));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setPaddingBottom(5);
        refTable.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Reference Number", PdfConstants.TABLE_HEADER));
        cell2.setBackgroundColor(new GrayColor(0.75f));
        cell2.setPaddingBottom(5);
        refTable.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase("Reference Title", PdfConstants.TABLE_HEADER));
        cell3.setBackgroundColor(new GrayColor(0.75f));
        cell3.setPaddingBottom(5);
        refTable.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Phrase("Issue", PdfConstants.TABLE_HEADER));
        cell4.setBackgroundColor(new GrayColor(0.75f));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setPaddingBottom(5);
        refTable.addCell(cell4);

        Integer count = 1;

        for (DocumentReference ref : references) {

            PdfPCell number = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
            number.setHorizontalAlignment(Element.ALIGN_CENTER);
            number.setPaddingBottom(5);
            refTable.addCell(number);

            PdfPCell refId = new PdfPCell(new Phrase(ref.getReference().getRefNumber(), PdfConstants.TABLE_CELL));
            refId.setPaddingBottom(5);
            refTable.addCell(refId);

            PdfPCell refTitle = new PdfPCell(new Phrase(ref.getReference().getRefTitle(), PdfConstants.TABLE_CELL));
            refTitle.setPaddingBottom(5);
            refTable.addCell(refTitle);

            PdfPCell revision = new PdfPCell(new Phrase(ref.getReference().getRefVersion(), PdfConstants.TABLE_CELL));
            revision.setHorizontalAlignment(Element.ALIGN_CENTER);
            revision.setPaddingBottom(5);
            refTable.addCell(revision);
        }

        Paragraph refTablePara = new Paragraph();
        refTablePara.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
        refTablePara.add(refTable);

        pdf.add(refTablePara);

        // Blank line belo table
        Paragraph refPara = new Paragraph();
        documentPdfService.addEmptyLine(refPara, 1);
        pdf.add(refPara);
    }

    private void addAcronymsAbbreviations(Document document, com.itextpdf.text.Document pdf) throws DocumentException {

        float[] abbrevColWidths = {1, 3, 15};
        PdfPTable abbrevTable = new PdfPTable(abbrevColWidths);
        abbrevTable.setWidthPercentage(100);

        // Table headers
        PdfPCell abbrevCell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        abbrevCell1.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        abbrevCell1.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell1);

        PdfPCell abbrevCell2 = new PdfPCell(new Phrase("Term", PdfConstants.TABLE_HEADER));
        abbrevCell2.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell2.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell2);

        PdfPCell abbrevCell3 = new PdfPCell(new Phrase("Expanded Meaning", PdfConstants.TABLE_HEADER));
        abbrevCell3.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell3.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell3);

        Integer count = 1;

        for (Definition definition : document.getDefinitions()) {

            if (definition.getDefinitionType() == DefinitionTypes.ACRONYM || definition.getDefinitionType() == DefinitionTypes.ABBREVIATION) {
                
                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
                number.setHorizontalAlignment(Element.ALIGN_CENTER);
                number.setPaddingBottom(5);
                abbrevTable.addCell(number);

                PdfPCell term = new PdfPCell(new Phrase(definition.getTerm(), PdfConstants.TABLE_CELL));
                term.setPaddingBottom(5);
                abbrevTable.addCell(term);

                PdfPCell termDef = new PdfPCell(new Phrase(definition.getTermDefinition(), PdfConstants.TABLE_CELL));
                termDef.setPaddingBottom(5);
                abbrevTable.addCell(termDef);
            }
        }

        Paragraph abbrevTablePara = new Paragraph();
        abbrevTablePara.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
        abbrevTablePara.add(abbrevTable);

        pdf.add(abbrevTablePara);

        // Blank line below table
        Paragraph abbrevPara = new Paragraph();
        documentPdfService.addEmptyLine(abbrevPara, 1);
        pdf.add(abbrevPara);
    }

    private void addAbbreviations(Document document, com.itextpdf.text.Document pdf) throws DocumentException {

        float[] abbrevColWidths = {1, 3, 15};
        PdfPTable abbrevTable = new PdfPTable(abbrevColWidths);
        abbrevTable.setWidthPercentage(100);

        // Table headers
        PdfPCell abbrevCell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        abbrevCell1.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        abbrevCell1.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell1);

        PdfPCell abbrevCell2 = new PdfPCell(new Phrase("Term", PdfConstants.TABLE_HEADER));
        abbrevCell2.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell2.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell2);

        PdfPCell abbrevCell3 = new PdfPCell(new Phrase("Expanded Meaning", PdfConstants.TABLE_HEADER));
        abbrevCell3.setBackgroundColor(new GrayColor(0.75f));
        abbrevCell3.setPaddingBottom(5);
        abbrevTable.addCell(abbrevCell3);

        Integer count = 1;

        for (Definition definition : document.getDefinitions()) {

            if (definition.getDefinitionType() == DefinitionTypes.ABBREVIATION) {
                
                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
                number.setHorizontalAlignment(Element.ALIGN_CENTER);
                number.setPaddingBottom(5);
                abbrevTable.addCell(number);

                PdfPCell term = new PdfPCell(new Phrase(definition.getTerm(), PdfConstants.TABLE_CELL));
                term.setPaddingBottom(5);
                abbrevTable.addCell(term);

                PdfPCell termDef = new PdfPCell(new Phrase(definition.getTermDefinition(), PdfConstants.TABLE_CELL));
                termDef.setPaddingBottom(5);
                abbrevTable.addCell(termDef);
            }
        }

        Paragraph abbrevTablePara = new Paragraph();
        abbrevTablePara.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
        abbrevTablePara.add(abbrevTable);

        pdf.add(abbrevTablePara);

        // Blank line below table
        Paragraph abbrevPara = new Paragraph();
        documentPdfService.addEmptyLine(abbrevPara, 1);
        pdf.add(abbrevPara);
    }

    private void addAcronyms(Document document, com.itextpdf.text.Document pdf) throws DocumentException {

        float[] acronymColWidths = {1, 3, 15};
        PdfPTable acronymTable = new PdfPTable(acronymColWidths);
        acronymTable.setWidthPercentage(100);

        // Table headers
        PdfPCell acronymCell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        acronymCell1.setBackgroundColor(new GrayColor(0.75f));
        acronymCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        acronymCell1.setPaddingBottom(5);
        acronymTable.addCell(acronymCell1);

        PdfPCell acronymCell2 = new PdfPCell(new Phrase("Term", PdfConstants.TABLE_HEADER));
        acronymCell2.setBackgroundColor(new GrayColor(0.75f));
        acronymCell2.setPaddingBottom(5);
        acronymTable.addCell(acronymCell2);

        PdfPCell acronymCell3 = new PdfPCell(new Phrase("Acronym Expanded Meaning", PdfConstants.TABLE_HEADER));
        acronymCell3.setBackgroundColor(new GrayColor(0.75f));
        acronymCell3.setPaddingBottom(5);
        acronymTable.addCell(acronymCell3);

        Integer count = 1;

        for (Definition acronym : document.getDefinitions()) {

            if (acronym.getDefinitionType() == DefinitionTypes.ACRONYM) {
                
                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
                number.setHorizontalAlignment(Element.ALIGN_CENTER);
                number.setPaddingBottom(5);
                acronymTable.addCell(number);

                PdfPCell term = new PdfPCell(new Phrase(acronym.getTerm(), PdfConstants.TABLE_CELL));
                term.setPaddingBottom(5);
                acronymTable.addCell(term);

                PdfPCell termDef = new PdfPCell(new Phrase(acronym.getTermDefinition(), PdfConstants.TABLE_CELL));
                termDef.setPaddingBottom(5);
                acronymTable.addCell(termDef);
            }
        }

        Paragraph acronymTablePara = new Paragraph();
        acronymTablePara.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
        acronymTablePara.add(acronymTable);

        pdf.add(acronymTablePara);

        // Blank line belo table
        Paragraph acronymPara = new Paragraph();
        documentPdfService.addEmptyLine(acronymPara, 1);
        pdf.add(acronymPara);
    }

    private void addDefinitions(Document document, com.itextpdf.text.Document pdf) throws DocumentException {

        float[] defColWidths = {1, 3, 15};
        PdfPTable defTable = new PdfPTable(defColWidths);
        defTable.setWidthPercentage(100);

        // Table headers
        PdfPCell defCell1 = new PdfPCell(new Phrase("#", PdfConstants.TABLE_HEADER));
        defCell1.setBackgroundColor(new GrayColor(0.75f));
        defCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        defCell1.setPaddingBottom(5);
        defTable.addCell(defCell1);

        PdfPCell defCell2 = new PdfPCell(new Phrase("Term", PdfConstants.TABLE_HEADER));
        defCell2.setBackgroundColor(new GrayColor(0.75f));
        defCell2.setPaddingBottom(5);
        defTable.addCell(defCell2);

        PdfPCell defCell3 = new PdfPCell(new Phrase("Definition", PdfConstants.TABLE_HEADER));
        defCell3.setBackgroundColor(new GrayColor(0.75f));
        defCell3.setPaddingBottom(5);
        defTable.addCell(defCell3);

        Integer count = 1;

        for (Definition definition : document.getDefinitions()) {

            if (definition.getDefinitionType() == DefinitionTypes.DEFINITION) {
                
                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(count++), PdfConstants.TABLE_CELL));
                number.setHorizontalAlignment(Element.ALIGN_CENTER);
                number.setPaddingBottom(5);
                defTable.addCell(number);

                PdfPCell term = new PdfPCell(new Phrase(definition.getTerm(), PdfConstants.TABLE_CELL_BOLD));
                term.setPaddingBottom(5);
                defTable.addCell(term);

                PdfPCell defn = new PdfPCell(new Phrase(definition.getTermDefinition(), PdfConstants.TABLE_CELL));
                defn.setPaddingBottom(5);
                defTable.addCell(defn);
            }
        }

        Paragraph defTablePara = new Paragraph();
        defTablePara.setIndentationLeft(PdfConstants.TEXT_INDENT_LEFT);
        defTablePara.add(defTable);

        pdf.add(defTablePara);

        // Blank line belo table
        Paragraph defPara = new Paragraph();
        documentPdfService.addEmptyLine(defPara, 1);
        pdf.add(defPara);
    }

    public class TableOfContents extends PdfPageEventHelper {

        protected List<SimpleEntry<String, Integer>> toc = new ArrayList<>();

        @Override
        public void onGenericTag(PdfWriter writer, com.itextpdf.text.Document document, Rectangle rect, String text) {

            toc.add(new SimpleEntry(text, writer.getPageNumber()));
        }

        public List getTOC() {

            return toc;
        }
    }

    public class HeaderTable extends PdfPageEventHelper {

        PdfTemplate numberOfPages;
        
        protected PdfPTable header;
        protected float tableHeight;
        protected Document document;
        
        @Override
        public void onOpenDocument(PdfWriter writer, com.itextpdf.text.Document document) {
            
            numberOfPages = writer.getDirectContent().createTemplate(30, 16);
        }
        
        public HeaderTable(Document document) {

            this.document = document;
        }

        public float getTableHeight() {

            return tableHeight;
        }

        @Override
        public void onEndPage(PdfWriter writer, com.itextpdf.text.Document document) {

            if (writer.getPageNumber() == 1) {

                try {

                    addFrontHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ItemPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {

                    addBodyHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ItemPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void addFrontHeaderTable(PdfWriter writer, com.itextpdf.text.Document doc) throws Exception {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            User approver = null;

            ClaimType claimType = claimTypeRepository.findFirstByTypeName("DOCUMENT_APPROVER");
            Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, document.getId());
            UserClaim userClaim = userClaimRepository.findFirstByClaim(claim);

            if (userClaim != null) {

                approver = userClaim.getUser();
            }

            float[] colWidths = {2, 2, 2, 1, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // Logo cell
            Image image = Image.getInstance(PdfConstants.DEFUALT_LOGO_IMAGE);
            image.scaleToFit(1000f, 36f);
            cell = new PdfPCell(image);
            cell.setColspan(2);
            cell.setRowspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Document Type cell
            phrase = new Phrase();
            phrase.add(new Chunk("Document Type:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getDocumentType().getEntityTypeCode(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Reference Number Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Reference Number:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getIdentifier(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Date Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Date:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(dateFormat.format(document.getModifiedDate()), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Issue Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Issue:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getDocumentIssue(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Page Number Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Page:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(String.format("%d", writer.getPageNumber()), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Issuerer Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Prepared By:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getOwner().getFirstName() + " " + document.getOwner().getLastName(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Issuerer Contact Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Prepared By Contact:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getOwner().getEmail(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // File path Cell
            phrase = new Phrase();
            phrase.add(new Chunk("File:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getUuId(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Approver Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Approved By:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk((approver != null ? approver.getFirstName() + " " + approver.getLastName() : ""), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Approver Contact Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Approved By Contact:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk((approver != null ? approver.getEmail() : ""), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Information Classification Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Information Class:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getInfoClass().getClassName(), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Keywords Cell
            cell = new PdfPCell(new Phrase("Keywords:", PdfConstants.HEADER_LABEL));
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, doc.top() + ((doc.topMargin() + tableHeight) / 2), writer.getDirectContent());

            PdfContentByte canvas = writer.getDirectContent();
            canvas.moveTo(20, doc.top() + 6);
            canvas.lineTo(575, doc.top() + 6);
        }

        private void addBodyHeaderTable(PdfWriter writer, com.itextpdf.text.Document doc) throws Exception {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            float[] colWidths = {2, 2, 2, 1, 1, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // Logo cell
            Image image = Image.getInstance(PdfConstants.DEFUALT_LOGO_IMAGE);
            image.scaleToFit(1000f, 26f);
            cell = new PdfPCell(image);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Document Type cell
            phrase = new Phrase();
            phrase.add(new Chunk("Document Type:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getDocumentType().getEntityTypeCode(), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Reference Number Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Reference Number:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getIdentifier(), PdfConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Issuerer Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Prepared By:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getOwner().getFirstName() + " " + document.getOwner().getLastName(), PdfConstants.TABLE_CELL));
            header.addCell(phrase);
            
            // Information Classification Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Information Class:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getInfoClass().getClassName(), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Date Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Date:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(dateFormat.format(document.getModifiedDate()), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Issue Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Issue:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(document.getDocumentIssue(), PdfConstants.TABLE_CELL));
            header.addCell(phrase);

            // Page Number Cell
            phrase = new Phrase();
            phrase.add(new Chunk("Page:", PdfConstants.HEADER_LABEL));
            phrase.add(Chunk.NEWLINE);
            phrase.add(new Chunk(String.format("Page %d of ", writer.getPageNumber()), PdfConstants.TABLE_CELL)); 
            header.addCell(phrase);

            header.addCell(new PdfPCell(Image.getInstance(numberOfPages)));

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, doc.top() + ((doc.topMargin() + tableHeight) / 2), writer.getDirectContent());

            PdfContentByte canvas = writer.getDirectContent();
            canvas.moveTo(20, doc.top() + 10);
            canvas.lineTo(575, doc.top() + 10);
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, com.itextpdf.text.Document document) {
            
            ColumnText.showTextAligned(numberOfPages, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() -1)), 2, 2, 0);
        }
        
    }

    public class FooterTable extends PdfPageEventHelper {

        DateFormat yearFormat = new SimpleDateFormat("yyyy");

        protected PdfPTable footer;
        protected Document document;

        public FooterTable(Document document) {

            this.document = document;
        }
        
        @Override
        public void onEndPage(PdfWriter writer, com.itextpdf.text.Document document) {

            addFooterTable(writer);
        }

        private void addFooterTable(PdfWriter writer) {

            String company = document.getProject().getProjectSponsor().getCompanyName();
            String copyright = "Copyright \u00A9 " + yearFormat.format(document.getModifiedDate()) + " " + company + ". "
                    + "This document is the property of " + company + ". All rights reserved. No part of this document may be reproduced, "
                    + "distributed, or transmitted in any form or shown to any third party without the written consent of " + company + ". "
                    + "Any infringement of these conditions will lead to institution of legal proceedings. ";

            footer = new PdfPTable(1);
            footer.setTotalWidth(555);
            PdfPCell cell = new PdfPCell(new Phrase(copyright, PdfConstants.FOOTER_TEXT));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(cell);

            footer.writeSelectedRows(0, -1, 20, 34, writer.getDirectContent());
        }
    }
}
