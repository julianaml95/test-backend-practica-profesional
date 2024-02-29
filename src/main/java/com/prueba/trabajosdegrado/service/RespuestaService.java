package com.prueba.trabajosdegrado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Respuesta;
import com.prueba.trabajosdegrado.repository.RespuestaRepository;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    public Respuesta getRespuestaById(Integer respuestaId) {
        return respuestaRepository.getRespuestaById(respuestaId);
    }

    public Respuesta getRespuestaBySolicitud(Integer solicitudId) {
        return respuestaRepository.getRespuestaBySolicitud(solicitudId);
    }

    public Respuesta save(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    public void delete(Integer id) {
        respuestaRepository.deleteBySolicitudId(id);
    }
}
