package com.prueba.trabajosdegrado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

    @Query("SELECT d FROM Solicitud d WHERE d.solicitudId = ?1")
    Solicitud getSolicitudById(Integer solicitudId);

    @Query("SELECT d FROM Solicitud d WHERE d.estudiante = ?1")
    List<Solicitud> getAllByEstudiante(Integer estudiante);
}
