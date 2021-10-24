package com.app.toonme.filter.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.toonme.filter.exception.FileStorageException;
import com.app.toonme.filter.exception.FilteredFileNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageService {
	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService() {
		this.fileStorageLocation = Paths.get("download").toAbsolutePath().normalize();
		logger.debug("fileStorageLocation : " + fileStorageLocation);
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file, String newFileName) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Invalid path sequence " + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(newFileName + "." + extension);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			applyFilter(targetLocation);
			return newFileName + "." + extension;
		} catch (IOException ex) {
			throw new FileStorageException("Failed to upload file. Try again!", ex);
		}
	}

	public void applyFilter(Path targetLocation) {
		Mat img = Imgcodecs.imread(targetLocation.toString());
		Mat gray = new Mat();
		Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
		Mat blur = new Mat();
		Imgproc.medianBlur(gray, blur, 5);
		Mat edges = new Mat();
		Imgproc.adaptiveThreshold(blur, edges, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 9, 9);
		Mat colorImg = new Mat();
		Imgproc.bilateralFilter(edges, colorImg, 9, 250, 250);
		Mat cartoon = new Mat();
		Core.bitwise_and(colorImg, edges, cartoon);
		Imgcodecs.imwrite(targetLocation.toString(), cartoon);
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FilteredFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FilteredFileNotFoundException("File not found " + fileName, ex);
		}
	}
}
