package com.app.toonme.filter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.toonme.filter.controller.FileController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UploadFileResponse {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    
    }
    
    @Override
    public String toString() {
		return "fileName : "+fileName+ "\nfileDownloadUri : "+fileDownloadUri+"\nfileType : "+fileType+"\nsize : "+size; 
    	
    }

}
