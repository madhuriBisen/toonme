package com.app.toonme.filter;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilterApplication {
	final static Logger logger = LoggerFactory.getLogger(FilterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FilterApplication.class, args);
		nu.pattern.OpenCV.loadLocally();

	}

}
