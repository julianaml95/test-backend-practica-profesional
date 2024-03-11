package com.prueba.trabajosdegrado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Evaluacion;
import com.prueba.trabajosdegrado.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> getEvaluacionesByRespuesta(Integer respuestaId) {
        return evaluacionRepository.findByRespuestaId(respuestaId);
    }

    public Evaluacion getEvaluacionById(Integer evaluacion) {
        return evaluacionRepository.getEvaluacionById(evaluacion);
    }

    public Evaluacion save(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public void delete(Integer evaluacionId) {
        evaluacionRepository.deleteById(evaluacionId);
    }
}
