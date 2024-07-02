package com.scoreme.pdfread;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.text.PDFTextStripper;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private static final int IMAGE_SIZE_THRESHOLD = 50000; 

    public static boolean isPdfEmpty(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            if (document.getNumberOfPages() == 0) {
                logger.info("PDF is empty: No pages found.");
                return true;
            }

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document).trim();
            logger.info("PDF text content: {}", text);
            return text.isEmpty();
        }
    }

    public static String extractText(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public static boolean containsImages(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            for (PDPage page : document.getPages()) {
                PDResources resources = page.getResources();
                for (COSName xObjectName : resources.getXObjectNames()) {
                    PDXObject xObject = resources.getXObject(xObjectName);
                    if (xObject instanceof PDImageXObject) {
                        PDImageXObject imageXObject = (PDImageXObject) xObject;
                        if (imageXObject.getCOSObject().getLength() > IMAGE_SIZE_THRESHOLD) {
                            logger.info("Significant image found in PDF.");
                            return true;
                        }
                    }
                }
            }
        }
        logger.info("No significant images found in PDF.");
        return false;
    }

    public static boolean isScannedPdf(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document).trim();
            boolean containsImages = containsImages(pdfBytes);
            logger.info("Is scanned PDF: Text is empty: {} or contains significant images: {}", text.isEmpty(), containsImages);
            return text.isEmpty() || containsImages;
        }
    }

    public static boolean isOcrGeneratedPdf(byte[] pdfBytes) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); 
        tesseract.setLanguage("eng");

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            for (PDPage page : document.getPages()) {
                PDResources resources = page.getResources();
                for (COSName xObjectName : resources.getXObjectNames()) {
                    PDXObject xObject = resources.getXObject(xObjectName);
                    if (xObject instanceof PDImageXObject) {
                        PDImageXObject imageXObject = (PDImageXObject) xObject;
                        if (imageXObject.getCOSObject().getLength() > IMAGE_SIZE_THRESHOLD) {
                            BufferedImage bufferedImage = imageXObject.getImage();
                            if (bufferedImage != null) {
                                String ocrResult = tesseract.doOCR(bufferedImage);
                                if (!ocrResult.trim().isEmpty()) {
                                    logger.info("OCR text found in image.");
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.info("No OCR text found in PDF.");
        return false;
    }
}



