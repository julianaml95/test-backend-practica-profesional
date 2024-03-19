package com.prueba.trabajosdegrado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Resolucion;

import jakarta.transaction.Transactional;

public interface ResolucionRepository extends JpaRepository<Resolucion, Integer> {
    @Query("SELECT d FROM Resolucion d WHERE d.id = ?1")
    Resolucion getResolucionById(Integer id);

    @Query("SELECT r FROM Resolucion r WHERE r.solicitud = ?1")
    Resolucion getResolucionBySolicitud(Integer solicitud);

    @Transactional
    @Modifying
    @Query("DELETE FROM Resolucion r WHERE r.solicitud = ?1")
    void deleteBySolicitudId(Integer solicitudId);
}
