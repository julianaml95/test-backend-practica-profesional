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

import com.prueba.trabajosdegrado.model.Experto;
import com.prueba.trabajosdegrado.service.ExpertoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/expertos")
public class ExpertoController {
    @Autowired
    private ExpertoService expertoService;

    @PostMapping
    public ResponseEntity<Experto> crearExperto(@RequestBody Experto experto) {
        Experto nuevoExperto = expertoService.crearExperto(experto);
        return ResponseEntity.ok(nuevoExperto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experto> obtenerExpertoPorId(@PathVariable Integer id) {
        Optional<Experto> expertoOptional = expertoService.obtenerExpertoPorId(id);
        return expertoOptional
                .map(experto -> ResponseEntity.ok(experto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Experto>> obtenerExpertos() {
        List<Experto> expertos = expertoService.getExpertos();
        return new ResponseEntity<>(expertos, HttpStatus.OK);
    }
}