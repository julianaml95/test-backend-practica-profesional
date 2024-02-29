package com.prueba.trabajosdegrado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Solicitud;
import com.prueba.trabajosdegrado.repository.SolicitudRepository;

@Service
public class SolicitudService {
    @Autowired
    private SolicitudRepository solicitudRepository;

    public Solicitud getSolicitudById(Integer solicitudId) {
        return solicitudRepository.getSolicitudById(solicitudId);
    }

    public List<Solicitud> getAllByEstudiante(Integer estudianteId) {
        return solicitudRepository.getAllByEstudiante(estudianteId);
    }

    public List<Solicitud> findAll() {
        return solicitudRepository.findAll();
    }

    public Solicitud save(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public void delete(Integer id) {
        solicitudRepository.deleteById(id);
    }
}
