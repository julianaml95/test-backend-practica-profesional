package com.prueba.trabajosdegrado.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prueba.trabajosdegrado.dto.FileResponse;
import com.prueba.trabajosdegrado.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/files")
public class FilesController {
        @Autowired
        private FileService fileService;

        @PostMapping("upload")
        public FileResponse uploadFile(@RequestParam("document") MultipartFile file,
                        @RequestParam("estudianteId") Integer estudianteId,
                        @RequestParam("tipoDocumento") String tipoDocumento) {
                String fileName = fileService.storeFile(file, estudianteId, tipoDocumento);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/download/")
                                .path(fileName)
                                .toUriString();
                return new FileResponse(fileName, fileDownloadUri,
                                file.getContentType(), file.getSize());
        }

        @GetMapping("download")
        public ResponseEntity<Resource> downloadFile(@RequestParam("estudianteId") Integer estudianteId,
                        @RequestParam("tipoDocumento") String tipoDocumento,
                        HttpServletRequest request) {
                String fileName = fileService.getDocumentName(estudianteId, tipoDocumento);
                System.out.println(fileName);
                Resource resource = null;
                if (fileName != null && !fileName.isEmpty()) {
                        try {
                                resource = fileService.loadFileAsResource(fileName);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                        // Try to determine file's content type
                        String contentType = null;
                        try {
                                contentType = request.getServletContext()
                                                .getMimeType(resource.getFile().getAbsolutePath());
                        } catch (IOException ex) {
                                // logger.info("Could not determine file type.");
                        }
                        // Fallback to the default content type if type could not be determined
                        if (contentType == null) {
                                contentType = "application/octet-stream";
                        }
                        return ResponseEntity.ok()
                                        .contentType(MediaType.parseMediaType(contentType))
                                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                                        "attachment; filename=\"" + resource.getFilename() + "\"")
                                        .body(resource);
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("delete")
        public ResponseEntity<Map<String, String>> deleteFile(@RequestParam("estudianteId") Integer estudianteId,
                        @RequestParam("tipoDocumento") String tipoDocumento) {
                Map<String, String> response = new HashMap<>();
                try {
                        fileService.deleteFile(estudianteId, tipoDocumento);
                        response.put("message", "Archivo eliminado correctamente");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        response.put("message", "Error al eliminar el archivo: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }
}