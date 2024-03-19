package com.prueba.trabajosdegrado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Evaluacion;

import jakarta.transaction.Transactional;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    @Query("SELECT d FROM Evaluacion d WHERE d.id = ?1")
    Evaluacion getEvaluacionById(Integer id);

    List<Evaluacion> findByRespuestaId(Integer respuesta);

    @Transactional
    @Modifying
    @Query("DELETE FROM Evaluacion r WHERE r.respuesta.id = ?1")
    void deleteByRespuesta(Integer respuestaId);
}
