package com.prueba.trabajosdegrado.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trabajosdegrado.model.Docente;
import com.prueba.trabajosdegrado.service.DocenteService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/docentes")
public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    @PostMapping
    public ResponseEntity<Docente> crearDocente(@RequestBody Docente docente) {
        Docente nuevoDocente = docenteService.crearDocente(docente);
        return ResponseEntity.ok(nuevoDocente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> obtenerDocentePorId(@PathVariable Integer id) {
        Optional<Docente> docenteOptional = docenteService.obtenerDocentePorId(id);
        return docenteOptional
                .map(docente -> ResponseEntity.ok(docente))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Docente>> obtenerDocentes() {
        List<Docente> docentes = docenteService.getDocentes();
        return new ResponseEntity<>(docentes, HttpStatus.OK);
    }

}