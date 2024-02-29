package com.prueba.trabajosdegrado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Respuesta;

import jakarta.transaction.Transactional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {

    @Query("SELECT d FROM Respuesta d WHERE d.respuestaId = ?1")
    Respuesta getRespuestaById(Integer respuestaId);

    @Query("SELECT d FROM Respuesta d WHERE d.solicitud = ?1")
    Respuesta getRespuestaBySolicitud(Integer solicitud);

    @Transactional
    @Modifying
    @Query("DELETE FROM Respuesta r WHERE r.solicitud = ?1")
    void deleteBySolicitudId(Integer solicitudId);
}
