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

import com.prueba.trabajosdegrado.model.Evaluacion;
import com.prueba.trabajosdegrado.model.Respuesta;
import com.prueba.trabajosdegrado.service.EvaluacionService;
import com.prueba.trabajosdegrado.service.RespuestaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping("respuesta/{respuestaId}")
    public ResponseEntity<Respuesta> obtenerRespuesta(@PathVariable Integer respuestaId) {
        Respuesta respuesta = respuestaService.getRespuestaById(respuestaId)
                .orElseThrow(() -> new RuntimeException("Not found respuesta with id = " + respuestaId));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("respuesta")
    public ResponseEntity<Respuesta> obtenerRespuestaBySolicitud(@RequestParam Integer solicitudId) {
        Respuesta respuesta = respuestaService.getRespuestaBySolicitud(solicitudId);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("respuesta")
    public ResponseEntity<Respuesta> guardarRespuesta(@RequestBody Respuesta respuesta) {
        return new ResponseEntity<Respuesta>(respuestaService.save(respuesta), HttpStatus.OK);
    }

    @DeleteMapping("/respuesta/{solicitudId}")
    public ResponseEntity<HttpStatus> deleteRespuesta(@PathVariable Integer solicitudId) {
        respuestaService.delete(solicitudId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("respuesta/{solicitudId}")
    public ResponseEntity<Respuesta> actualizarRespuesta(@RequestBody Respuesta respuesta,
            @PathVariable Integer solicitudId) throws Exception {

        Respuesta _respuesta = respuestaService.getRespuestaBySolicitud(solicitudId);

        if (_respuesta == null) {
            throw new Exception("Not found product with the id " + solicitudId);
        }

        _respuesta.setFecha_correcciones(respuesta.getFecha_correcciones());
        _respuesta.setTitulo(respuesta.getTitulo());

        return new ResponseEntity<Respuesta>(respuestaService.save(_respuesta),
                HttpStatus.OK);
    }

    ///////////////
    // EVALUACIONES
    @PostMapping("/respuesta/{respuestaId}/evaluaciones")
    public ResponseEntity<Evaluacion> createEvaluacion(@PathVariable Integer respuestaId,
            @RequestBody Evaluacion evaluacion) {
        Respuesta respuesta = respuestaService.getRespuestaById(respuestaId).orElseThrow();
        evaluacion.setRespuesta(respuesta);
        Evaluacion _evaluacion = evaluacionService.save(evaluacion);
        return new ResponseEntity<>(_evaluacion, HttpStatus.CREATED);
    }

    @GetMapping("/respuesta/{respuestaId}/evaluaciones")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesByRespuesta(@PathVariable Integer respuestaId) {
        List<Evaluacion> evaluaciones = evaluacionService.getEvaluacionesByRespuesta(respuestaId);
        return new ResponseEntity<>(evaluaciones, HttpStatus.OK);
    }

    @PatchMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<Evaluacion> updateEvaluacion(@PathVariable Integer evaluacionId,
            @RequestBody Evaluacion evaluacion) {
        Evaluacion _evaluacion = evaluacionService.getEvaluacionById(evaluacionId);

        _evaluacion.setEstadoRespuesta(evaluacion.getEstadoRespuesta());
        return new ResponseEntity<>(evaluacionService.save(_evaluacion), HttpStatus.CREATED);
    }

    @DeleteMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<HttpStatus> deleteEvaluacion(@PathVariable Integer evaluacionId) {
        evaluacionService.delete(evaluacionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    ////////////////
}
