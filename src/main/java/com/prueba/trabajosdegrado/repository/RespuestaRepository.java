package com.prueba.trabajosdegrado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Respuesta;

import jakarta.transaction.Transactional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {

    @Query("SELECT d FROM Respuesta d WHERE d.id = ?1")
    Optional<Respuesta> getRespuestaById(Integer id);

    @Query("SELECT d FROM Respuesta d WHERE d.solicitud = ?1")
    Respuesta getRespuestaBySolicitud(Integer solicitud);

    @Transactional
    @Modifying
    @Query("DELETE FROM Respuesta r WHERE r.solicitud = ?1")
    void deleteBySolicitudId(Integer solicitudId);
}
