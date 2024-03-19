package com.prueba.trabajosdegrado.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Resolucion;
import com.prueba.trabajosdegrado.repository.ResolucionRepository;

@Service
public class ResolucionService {
    @Autowired
    private ResolucionRepository resolucionRepository;

    public Resolucion getResolucionById(Integer resolucionId) {
        return resolucionRepository.getResolucionById(resolucionId);
    }

    public Resolucion getResolucionBySolicitud(Integer solicitudId) {
        return resolucionRepository.getResolucionBySolicitud(solicitudId);
    }

    public Resolucion save(Resolucion resolucion) {
        return resolucionRepository.save(resolucion);
    }

    public void delete(Integer id) {
        resolucionRepository.deleteBySolicitudId(id);
    }
}
