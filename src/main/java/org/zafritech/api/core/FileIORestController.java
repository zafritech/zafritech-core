/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zafritech.core.data.dao.UploadDao;
import org.zafritech.core.services.FileIOService;


/**
 *
 * @author LukeS
 */
@RestController
public class FileIORestController {
    
    @Autowired
    private FileIOService fileUploadService;
    
    @PostMapping("/api/upload/files/single")
     public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        
        if (uploadFile.isEmpty()) {
            
            return new ResponseEntity("Please select a file!", HttpStatus.OK);
        }

        try {

            fileUploadService.saveUploadedFiles(Arrays.asList(uploadFile));

        } catch (IOException e) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " + uploadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
     
    @PostMapping("/api/upload/files/multi")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("extraField") String extraField,
                                             @RequestParam("files") MultipartFile[] uploadFiles) {

        List<String> filePaths;

        // Get file name
        String uploadedFileName = Arrays.stream(uploadFiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        try {

            filePaths = fileUploadService.saveUploadedFiles(Arrays.asList(uploadFiles));

        } catch (IOException e) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(filePaths, HttpStatus.OK);
    }
    
    @PostMapping("/api/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadDao model) {

        try {

            fileUploadService.saveUploadedFiles(Arrays.asList(model.getFiles()));

        } catch (IOException e) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    }
}
