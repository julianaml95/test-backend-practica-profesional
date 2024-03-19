package com.prueba.trabajosdegrado.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trabajosdegrado.model.Resolucion;
import com.prueba.trabajosdegrado.service.ResolucionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api")
public class ResolucionController {
    @Autowired
    private ResolucionService resolucionService;

    @GetMapping("resolucion/{solicitudId}")
    public ResponseEntity<Resolucion> obtenerResolucion(@PathVariable Integer solicitudId) {
        Resolucion resolucion = resolucionService.getResolucionBySolicitud(solicitudId);
        return new ResponseEntity<>(resolucion, HttpStatus.OK);
    }

    @PostMapping("resolucion")
    public ResponseEntity<Resolucion> guardarResolucion(@RequestBody Resolucion resolucion) {
        return new ResponseEntity<Resolucion>(resolucionService.save(resolucion), HttpStatus.OK);
    }

    @PatchMapping("resolucion/{solicitudId}")
    public ResponseEntity<Resolucion> actualizarResolucion(@RequestBody Resolucion resolucion,
            @PathVariable Integer solicitudId) throws Exception {

        Resolucion _resolucion = resolucionService.getResolucionBySolicitud(solicitudId);

        if (_resolucion == null) {
            throw new Exception("Not found resolucion with the id " + solicitudId);
        }

        _resolucion.setTitulo(resolucion.getTitulo());
        _resolucion.setFechaActa(resolucion.getFechaActa());
        _resolucion.setFechaResolucion(resolucion.getFechaResolucion());
        _resolucion.setCodirector(resolucion.getCodirector());
        _resolucion.setDirector(resolucion.getDirector());
        _resolucion.setNumeroActaRevision(resolucion.getNumeroActaRevision());
        _resolucion.setNumeroResolucion(resolucion.getNumeroResolucion());

        return new ResponseEntity<Resolucion>(resolucionService.save(_resolucion), HttpStatus.OK);
    }

    @DeleteMapping("/resolucion/{solicitudId}")
    public ResponseEntity<HttpStatus> deleteResolucion(@PathVariable Integer solicitudId) {
        resolucionService.delete(solicitudId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
