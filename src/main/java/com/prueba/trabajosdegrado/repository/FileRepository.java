package com.prueba.trabajosdegrado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Document;

import jakarta.transaction.Transactional;

public interface FileRepository extends JpaRepository<Document, Integer> {
    @Query("SELECT d FROM Document d WHERE d.solicitudId = ?1 AND d.documentType = ?2")
    Document checkDocumentBySolicitudId(Integer solicitudId, String docType);

    @Query("SELECT d FROM Document d WHERE d.evaluacionId = ?1 AND d.documentType = ?2")
    Document checkDocumentByEvaluacionId(Integer evaluacionId, String docType);

    @Query("SELECT d FROM Document d WHERE d.resolucionId = ?1 AND d.documentType = ?2")
    Document checkDocumentByResolucionId(Integer resolucionId, String docType);

    @Query("SELECT d.fileName FROM Document d WHERE d.solicitudId = ?1 AND d.documentType = ?2")
    String getUploadSolicitudDocumentPath(Integer solicitudId, String docType);

    @Query("SELECT d.fileName FROM Document d WHERE d.evaluacionId = ?1 AND d.documentType = ?2")
    String getUploadEvaluacionDocumentPath(Integer evaluacionId, String docType);

    @Query("SELECT d.fileName FROM Document d WHERE d.resolucionId = ?1 AND d.documentType = ?2")
    String getUploadResolucionDocumentPath(Integer resolucionId, String docType);

    @Transactional
    @Modifying
    @Query("DELETE FROM Document d WHERE d.evaluacionId = ?1")
    void deleteAllByEvaluacionId(Integer evaluacionId);
}
