package com.scoreme.pdfread;

import com.scoreme.pdfread.PdfAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/test")
public class PDFAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(PDFAnalysisController.class);

    @Autowired
    private PdfAnalysisService pdfAnalysisService;

    @PostMapping("/upload")
    public ResponseEntity<String> analyzePdf(@RequestParam("file") MultipartFile file) {
        logger.debug("Received file upload request");
    logger.debug("File name: {}", file.getOriginalFilename());
    logger.debug("File size: {} bytes", file.getSize());
        
        try {
            if (file.isEmpty()) {
                logger.warn("Uploaded file is empty");
                return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
            }

            String result = pdfAnalysisService.analyzePdf(file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error processing file: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error processing file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
