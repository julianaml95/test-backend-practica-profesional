package com.prueba.trabajosdegrado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Document;

public interface FileRepository extends JpaRepository<Document, Integer> {
    @Query("SELECT d FROM Document d WHERE d.userId = ?1 AND d.documentType = ?2")
    Document checkDocumentByUserId(Integer userId, String docType);

    @Query("SELECT d.fileName FROM Document d WHERE d.userId = ?1 AND d.documentType = ?2")
    String getUploadDocumentPath(Integer userId, String docType);
}
