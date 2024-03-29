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
import com.prueba.trabajosdegrado.model.Evaluacion;
import com.prueba.trabajosdegrado.model.Resolucion;
import com.prueba.trabajosdegrado.model.Solicitud;
import com.prueba.trabajosdegrado.service.EvaluacionService;
import com.prueba.trabajosdegrado.service.FileService;
import com.prueba.trabajosdegrado.service.ResolucionService;
import com.prueba.trabajosdegrado.service.SolicitudService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/files")
public class FilesController {
        @Autowired
        private FileService fileService;

        @Autowired
        private SolicitudService solicitudService;

        @Autowired
        private EvaluacionService evaluacionService;

        @Autowired
        private ResolucionService resolucionService;

        @PostMapping("upload")
        public FileResponse uploadFilesSolicitud(@RequestParam("document") MultipartFile file,
                        @RequestParam(name = "solicitudId", required = false) Integer solicitudId,
                        @RequestParam(name = "evaluacionId", required = false) Integer evaluacionId,
                        @RequestParam(name = "resolucionId", required = false) Integer resolucionId,
                        @RequestParam("tipoDocumento") String tipoDocumento) {

                Boolean isSolicitud = solicitudId != null;
                Boolean isEvaluacion = evaluacionId != null;
                Boolean isResolucion = resolucionId != null;

                String fileName = "";

                if (isSolicitud) {
                        fileName = fileService.storeFile(file, solicitudId,
                                        "solicitudId",
                                        tipoDocumento);
                        Solicitud _solicitud = solicitudService.getSolicitudById(solicitudId);
                        actualizarDocumentoSolicitud(_solicitud, tipoDocumento, fileName);
                        solicitudService.save(_solicitud);
                }

                if (isEvaluacion) {
                        fileName = fileService.storeFile(file, evaluacionId,
                                        "evaluacionId",
                                        tipoDocumento);
                        Evaluacion _evaluacion = evaluacionService.getEvaluacionById(evaluacionId);
                        actualizarDocumentoEvaluacion(_evaluacion, tipoDocumento, fileName);
                        evaluacionService.save(_evaluacion);
                }

                if (isResolucion) {
                        fileName = fileService.storeFile(file, resolucionId,
                                        "resolucionId",
                                        tipoDocumento);
                        Resolucion _resolucion = resolucionService.getResolucionById(resolucionId);
                        actualizarDocumentoResolucion(_resolucion, tipoDocumento, fileName);
                        resolucionService.save(_resolucion);
                }

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/download/")
                                .path(fileName)
                                .toUriString();
                return new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        }

        private void actualizarDocumentoSolicitud(Solicitud solicitud, String tipoDocumento, String fileName) {
                switch (tipoDocumento) {
                        case "doc_solicitud_valoracion":
                                solicitud.setDocSolicitudValoracion(fileName);
                                break;
                        case "doc_anteproyecto_examen":
                                solicitud.setDocAnteproyectoExamen(fileName);
                                break;
                        case "doc_examen_valoracion":
                                solicitud.setDocExamenValoracion(fileName);
                                break;
                        case "doc_oficio_jurados":
                                solicitud.setDocOficioJurados(fileName);
                                break;
                        default:
                                // Manejar el caso por defecto
                }
        }

        private void actualizarDocumentoEvaluacion(Evaluacion evaluacion, String tipoDocumento, String fileName) {
                switch (tipoDocumento) {
                        case "docFormatoB":
                                evaluacion.setDocFormatoB(fileName);
                                break;
                        case "docFormatoC":
                                evaluacion.setDocFormatoC(fileName);
                                break;
                        case "docObservaciones":
                                evaluacion.setDocObservaciones(fileName);
                                break;
                        default:
                                // Manejar el caso por defecto
                }
        }

        private void actualizarDocumentoResolucion(Resolucion resolucion, String tipoDocumento, String fileName) {
                switch (tipoDocumento) {
                        case "anteproyectoAprobado":
                                resolucion.setAnteproyectoAprobado(fileName);
                                break;
                        case "solicitudComite":
                                resolucion.setSolicitudComite(fileName);
                                break;
                        case "solicitudConcejo":
                                resolucion.setSolicitudConcejo(fileName);
                                break;
                        case "resolucionConcejo":
                                resolucion.setResolucionConcejo(fileName);
                                break;
                        default:
                                // Manejar el caso por defecto
                }
        }

        @GetMapping("download")
        public ResponseEntity<Resource> downloadFile(
                        @RequestParam(name = "solicitudId", required = false) Integer solicitudId,
                        @RequestParam(name = "evaluacionId", required = false) Integer evaluacionId,
                        @RequestParam(name = "resolucionId", required = false) Integer resolucionId,
                        @RequestParam("tipoDocumento") String tipoDocumento,
                        HttpServletRequest request) {

                String fileName = null;
                Boolean isSolicitud = solicitudId != null;
                Boolean isEvaluacion = evaluacionId != null;
                Boolean isResolucion = resolucionId != null;

                if (isSolicitud) {
                        fileName = fileService.getDocumentSolicitudName(solicitudId, tipoDocumento);
                }

                if (isEvaluacion) {
                        fileName = fileService.getDocumentEvaluacionName(evaluacionId, tipoDocumento);
                }

                if (isResolucion) {
                        fileName = fileService.getDocumentResolucionName(resolucionId, tipoDocumento);
                }

                if (fileName != null && !fileName.isEmpty()) {
                        Resource resource = null;
                        try {
                                resource = fileService.loadFileAsResource(fileName);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                        String contentType = null;
                        try {
                                contentType = request.getServletContext()
                                                .getMimeType(resource.getFile().getAbsolutePath());
                        } catch (IOException ex) {
                                // logger.info("Could not determine file type.");
                        }
                        if (contentType == null) {
                                contentType = "application/octet-stream";
                        }
                        if (fileName.toLowerCase().endsWith(".rar")) {
                                contentType = "application/x-zip-compressed";
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

        @DeleteMapping("delete/all")
        public ResponseEntity<Map<String, String>> deleteDocumentos(
                        @RequestParam("evaluacionId") Integer evaluacionId) {
                Map<String, String> response = new HashMap<>();
                try {
                        fileService.deleteFiles(evaluacionId);
                        response.put("message", "Archivos eliminados correctamente");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        response.put("message", "Error al eliminar los archivos: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        @DeleteMapping("delete")
        public ResponseEntity<Map<String, String>> deleteDocumento(
                        @RequestParam(name = "solicitudId", required = false) Integer solicitudId,
                        @RequestParam(name = "evaluacionId", required = false) Integer evaluacionId,
                        @RequestParam(name = "resolucionId", required = false) Integer resolucionId,
                        @RequestParam("tipoDocumento") String tipoDocumento) {
                Map<String, String> response = new HashMap<>();
                Boolean isSolicitud = solicitudId != null;
                Boolean isEvaluacion = evaluacionId != null;
                Boolean isResolucion = resolucionId != null;
                try {
                        if (isSolicitud) {
                                fileService.deleteFile(solicitudId, "solicitudId", tipoDocumento);
                                Solicitud _solicitud = solicitudService.getSolicitudById(solicitudId);

                                switch (tipoDocumento) {
                                        case "doc_solicitud_valoracion":
                                                _solicitud.setDocSolicitudValoracion(null);
                                                break;
                                        case "doc_anteproyecto_examen":
                                                _solicitud.setDocAnteproyectoExamen(null);
                                                break;
                                        case "doc_examen_valoracion":
                                                _solicitud.setDocExamenValoracion(null);
                                                break;
                                        case "doc_oficio_jurados":
                                                _solicitud.setDocOficioJurados(null);
                                                break;
                                        default:
                                                // Manejar el caso por defecto
                                }

                                solicitudService.save(_solicitud);
                        }

                        if (isEvaluacion) {
                                fileService.deleteFile(evaluacionId, "evaluacionId", tipoDocumento);
                                Evaluacion _evaluacion = evaluacionService.getEvaluacionById(evaluacionId);

                                switch (tipoDocumento) {
                                        case "docFormatoB":
                                                _evaluacion.setDocFormatoB(null);
                                                break;
                                        case "docFormatoC":
                                                _evaluacion.setDocFormatoC(null);
                                                break;
                                        case "docObservaciones":
                                                _evaluacion.setDocObservaciones(null);
                                                break;

                                        default:
                                                // Manejar el caso por defecto
                                }

                                evaluacionService.save(_evaluacion);
                        }

                        if (isResolucion) {
                                fileService.deleteFile(resolucionId, "resolucionId", tipoDocumento);
                                Resolucion _resolucion = resolucionService.getResolucionById(resolucionId);

                                switch (tipoDocumento) {
                                        case "anteproyectoAprobado":
                                                _resolucion.setAnteproyectoAprobado(null);
                                        case "solicitudComite":
                                                _resolucion.setSolicitudComite(null);
                                                break;
                                        case "solicitudConcejo":
                                                _resolucion.setSolicitudConcejo(null);
                                                break;
                                        case "resolucionConcejo":
                                                _resolucion.setResolucionConcejo(null);
                                                break;
                                        default:
                                                // Manejar el caso por defecto
                                }

                                resolucionService.save(_resolucion);
                        }

                        response.put("message", "Archivo eliminado correctamente");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        response.put("message", "Error al eliminar el archivo: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

}