**PDF Differentiator using Spring Boot**
 

**Overview :** 

The PDF Differentiator is a Spring Boot application designed to analyze PDFs and determine whether they are scanned or computer-generated. It utilizes a combination of text extraction, image detection, metadata analysis, and Optical Character Recognition (OCR) to achieve this. The application is capable of handling various types of PDFs, including those with no text or image content.

**Features :**

Text Extraction: Extracts and analyzes text to identify if the PDF is computer-generated.
Image Detection: Detects and analyzes images to determine if the PDF is scanned.
Metadata Analysis: Analyzes metadata to gather information about the PDF's origin and creation.
OCR Integration: Uses OCR to extract text from scanned images for further analysis.
Empty PDF Handling: Identifies and processes empty PDFs effectively.
Spring Boot Powered: Leverages the Spring Boot framework for rapid development and deployment.

**Prerequisites :** 

Java 11 or later
Maven for build and dependency management
Tesseract OCR (for OCR functionality)

**Installation ->**

_Clone the repository:_

git clone https://github.com/geetansh14/PDF-Differentiator-using-Spring-Boot.git
cd PDF-Differentiator-using-Spring-Boot

_Build the project using Maven :_

mvn clean install

_Run the application :_

java -jar target/pdf-differentiator-0.0.1-SNAPSHOT.jar

Use Postman to get the response or run the HTML page on a live server to view the results.

**Usage**

_Upload a PDF: You can upload a PDF file through the application interface.
Analysis: The application will analyze the PDF to determine if it is scanned or computer-generated.
Results: The results of the analysis will be displayed, including text, image, and metadata details._

You can configure the application settings in the application.properties file located in the src/main/resources directory. Modify the following settings as needed:

OCR Configuration: Specify the path to the Tesseract OCR installation.

tesseract.path=/usr/local/bin/tesseract
Upload Directory: Set the directory for storing uploaded PDFs.

file.upload-dir=/uploads

**Dependencies**
Make sure to include the following dependencies in your pom.xml file to ensure proper functionality of logging and OCR extraction:

xml
Copy code
<dependencies>
    <!-- SLF4J for logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.32</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.32</version>
    </dependency>
    
    <!-- Apache Commons for utilities -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.8.0</version>
    </dependency>

    <!-- Tesseract for OCR -->
    <dependency>
        <groupId>net.sourceforge.tess4j</groupId>
        <artifactId>tess4j</artifactId>
        <version>4.5.4</version>
    </dependency>
</dependencies>

If the program does not run properly, you may need to adjust these dependencies and versions in your pom.xml file.

Also install tesseract beforehand and make sure to adjust the tess datapath in the file and add it to your system environment variables as well.

Contributions are welcome! Please follow these steps:

_Fork the repository._
_Create a new branch:_

_git checkout -b feature/YourFeature_
_Make your changes._
_Commit your changes:_

_git commit -m 'Add some feature'_
_Push to the branch:_

_git push origin feature/YourFeature_
_Open a pull request._

