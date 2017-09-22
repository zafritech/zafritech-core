/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LukeS
 */
public interface FileIOService {
    
    String getUploadDirectory();
    
    List<String> saveUploadedFiles(List<MultipartFile> files)  throws IOException;
}
