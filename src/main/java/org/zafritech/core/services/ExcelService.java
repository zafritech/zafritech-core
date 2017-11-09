/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author LukeS
 */
public interface ExcelService {
    
    Workbook getExcelWorkbook(FileInputStream inputStream, String filePath) throws IOException;
    
    Object getExcelCellValue(Cell cell);
    
    Map<String, CellStyle> getExcelStyles(Workbook wb);
}
