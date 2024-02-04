package com.springboot.azure.controller;

import com.springboot.azure.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @GetMapping("fileupload")
    public String fileUploadProcessing() {
        return fileUploadService.fileUploadprocessing();
    }
}
