package com.prueba.trabajosdegrado.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trabajosdegrado.exception.FileException;
import com.prueba.trabajosdegrado.model.Document;
import com.prueba.trabajosdegrado.repository.FileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileService {
    private final Path fileLocation = Paths.get("uploads");;

    @Autowired
    private FileRepository fileRepository;

    public void init() {
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            throw new FileException("Could not create the directory where the uploaded files will be " +
                    "stored.", e);
        }
    }

    public String storeFile(MultipartFile file, Integer userId, String docType) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";

        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new FileException(
                        "Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            fileName = userId + "_" + docType + fileExtension;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Document doc = fileRepository.checkDocumentByUserId(userId, docType);
            if (doc != null) {
                doc.setDocumentFormat(file.getContentType());
                doc.setFileName(fileName);
                fileRepository.save(doc);
            } else {
                Document newDoc = new Document();
                newDoc.setUserId(userId);
                newDoc.setDocumentFormat(file.getContentType());
                newDoc.setFileName(fileName);
                newDoc.setDocumentType(docType);
                fileRepository.save(newDoc);
            }
            return fileName;
        } catch (IOException ex) {
            throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    public void deleteFile(Integer estudianteId, String tipoDocumento) throws Exception {
        try {
            Document document = fileRepository.checkDocumentByUserId(estudianteId, tipoDocumento);
            if (document != null) {
                fileRepository.delete(document);
            } else {
                throw new FileNotFoundException("Archivo no encontrado para el estudiante " + estudianteId
                        + " y tipo de documento " + tipoDocumento);
            }
        } catch (EntityNotFoundException ex) {
            throw new FileNotFoundException("File not found ");
        }
    }

    public String getDocumentName(Integer userId, String docType) {
        return fileRepository.getUploadDocumentPath(userId, docType);
    }
}
