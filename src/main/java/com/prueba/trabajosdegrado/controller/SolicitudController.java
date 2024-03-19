package com.prueba.trabajosdegrado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trabajosdegrado.model.Solicitud;
import com.prueba.trabajosdegrado.service.SolicitudService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api")
public class SolicitudController {
    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("solicitud/{solicitudId}")
    public ResponseEntity<Solicitud> obtenerSolicitud(@PathVariable Integer solicitudId) {
        Solicitud solicitud = solicitudService.getSolicitudById(solicitudId);
        return new ResponseEntity<>(solicitud, HttpStatus.OK);
    }

    @GetMapping("solicitud")
    public ResponseEntity<List<Solicitud>> obtenerSolicitudes(@RequestParam Integer estudianteId) {
        List<Solicitud> solicitudes = solicitudService.getAllByEstudiante(estudianteId);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    @PostMapping("solicitud")
    public ResponseEntity<Solicitud> guardarSolicitud(@RequestBody Solicitud solicitud) {
        return new ResponseEntity<Solicitud>(solicitudService.save(solicitud), HttpStatus.OK);
    }

    @DeleteMapping("/solicitud/{id}")
    public ResponseEntity<HttpStatus> deleteSolicitud(@PathVariable Integer id) {
        solicitudService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("solicitud/{solicitudId}")
    public ResponseEntity<Solicitud> actualizarSolicitud(@RequestBody Solicitud solicitud,
            @PathVariable Integer solicitudId) throws Exception {

        Solicitud _solicitud = solicitudService.getSolicitudById(solicitudId);

        if (_solicitud == null) {
            throw new Exception("Not found product with the id " + solicitudId);
        }

        _solicitud.setTitulo(solicitud.getTitulo());
        _solicitud.setEstado(solicitud.getEstado());
        _solicitud.setFecha_acta(solicitud.getFecha_acta());
        _solicitud.setDocente(solicitud.getDocente());
        _solicitud.setExperto(solicitud.getExperto());
        _solicitud.setNumero_acta(solicitud.getNumero_acta());

        return new ResponseEntity<Solicitud>(solicitudService.save(_solicitud), HttpStatus.OK);
    }
}
