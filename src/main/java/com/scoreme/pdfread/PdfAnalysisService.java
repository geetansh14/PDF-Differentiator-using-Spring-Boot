package com.scoreme.pdfread;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PdfAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(PdfAnalysisService.class);

    public String analyzePdf(MultipartFile file) throws Exception {
        byte[] pdfBytes = file.getBytes();
        logger.info("Processing PDF file: {}, size: {} bytes", file.getOriginalFilename(), pdfBytes.length);

        if (PdfUtil.isPdfEmpty(pdfBytes)) {
            return "The PDF is empty.";
        }

        boolean hasImages = PdfUtil.containsImages(pdfBytes);
        logger.info("PDF contains images: {}", hasImages);

        String extractedText = PdfUtil.extractText(pdfBytes);
        logger.info("Extracted text: {}", extractedText.isEmpty() ? "No text found" : "Text found");

        boolean isScanned = PdfUtil.isScannedPdf(pdfBytes);
        logger.info("Is PDF scanned: {}", isScanned);

        boolean isGeneratedByOCR = PdfUtil.isOcrGeneratedPdf(pdfBytes);
        logger.info("Is PDF OCR generated: {}", isGeneratedByOCR);

        if (isScanned && !isGeneratedByOCR) {
            return "The PDF is a scanned document.";
        } else if (isGeneratedByOCR) {
            return "The PDF is a scanned document with OCR text.";
        } else {
            return "The PDF is computer-generated.";
        }
    }
}
