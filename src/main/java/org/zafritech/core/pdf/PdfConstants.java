/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import org.springframework.stereotype.Component;

/**
 *
 * @author LukeS
 */
@Component
public class PdfConstants {
   
//    @Value("${zafritech.paths.images-dir}")
//    private static String images_dir;
    
    private PdfConstants() {
        
    }
    
    public static String DEFUALT_LOGO_IMAGE = "F://Projects//NetBeans//SpringBoot//zafritech//src//main//resources//static//images//zidingo-rms-logo.png";
//    @SuppressWarnings("StaticNonFinalUsedInInitialization")
//    public static String DEFUALT_LOGO_IMAGE = images_dir + "zidingo-rms-logo.png";
    
    // Page Margins
    public static float MARGIN_TOP_DEFAULT = 80f;
    public static float MARGIN_BOTTOM_DEFAULT = 60f;
    public static float MARGIN_LEFT_DEFAULT = 30f;
    public static float MARGIN_RIGHT_DEFAULT = 30f;
    public static float MARGIN_TOP_FIRST_PAGE = 120f;
    
    // Font Styles
    public static Font TITLE = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    public static Font SUBTITLE = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    public static Font NORMAL = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    public static Font HEADER1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    public static Font HEADER2 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    public static Font HEADER3 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    public static Font HEADER4 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    public static Font INDENTIFIER = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
    public static Font TABLE_HEADER = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    public static Font TABLE_CELL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    public static Font TABLE_CELL_BOLD = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    public static Font HEADER_LABEL = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
    public static Font FOOTER_TEXT = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
}
