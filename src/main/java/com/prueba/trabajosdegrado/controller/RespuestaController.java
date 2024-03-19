package com.prueba.trabajosdegrado.controller;

import java.util.ArrayList;
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

import com.prueba.trabajosdegrado.dto.EvaluacionDTO;
import com.prueba.trabajosdegrado.dto.EvaluacionResponse;
import com.prueba.trabajosdegrado.model.Docente;
import com.prueba.trabajosdegrado.model.Evaluacion;
import com.prueba.trabajosdegrado.model.Experto;
import com.prueba.trabajosdegrado.model.Respuesta;
import com.prueba.trabajosdegrado.repository.DocenteRepository;
import com.prueba.trabajosdegrado.repository.ExpertoRepository;
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

    @Autowired
    private ExpertoRepository expertoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

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

        _respuesta.setFinalizado(respuesta.getFinalizado());
        _respuesta.setTitulo(respuesta.getTitulo());

        return new ResponseEntity<Respuesta>(respuestaService.save(_respuesta),
                HttpStatus.OK);
    }

    ///////////////
    // EVALUACIONES
    @PostMapping("/respuesta/{respuestaId}/evaluaciones")
    public ResponseEntity<Evaluacion> createEvaluacion(@PathVariable Integer respuestaId,
            @RequestBody EvaluacionDTO evaluacionDTO) {
        Respuesta respuesta = respuestaService.getRespuestaById(respuestaId)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setRespuesta(respuesta);

        if (evaluacionDTO.getExperto() != null) {
            Experto experto = expertoRepository.getExpertoById(evaluacionDTO.getExperto()).orElseThrow();
            evaluacion.setExperto(experto);
        }

        if (evaluacionDTO.getDocente() != null) {
            Docente docente = docenteRepository.getDocenteById(evaluacionDTO.getDocente()).orElseThrow();
            evaluacion.setDocente(docente);
        }

        Evaluacion _evaluacion = evaluacionService.save(evaluacion);
        System.out.println(evaluacion);
        return new ResponseEntity<>(_evaluacion, HttpStatus.CREATED);
    }

    @GetMapping("/respuesta/{respuestaId}/evaluaciones")
    public ResponseEntity<List<EvaluacionResponse>> obtenerEvaluacionesByRespuesta(@PathVariable Integer respuestaId) {
        List<Evaluacion> evaluaciones = evaluacionService.getEvaluacionesByRespuesta(respuestaId);
        List<EvaluacionResponse> evaluacionesResponse = new ArrayList<>();

        for (Evaluacion evaluacion : evaluaciones) {
            EvaluacionResponse evaluacionResponse = new EvaluacionResponse();
            evaluacionResponse.setId(evaluacion.getId());
            evaluacionResponse.setDocente(evaluacion.getDocente() != null ? evaluacion.getDocente().getId() : null);
            evaluacionResponse.setExperto(evaluacion.getExperto() != null ? evaluacion.getExperto().getId() : null);
            evaluacionResponse.setDocFormatoB(evaluacion.getDocFormatoB());
            evaluacionResponse.setDocFormatoC(evaluacion.getDocFormatoC());
            evaluacionResponse.setDocObservaciones(evaluacion.getDocObservaciones());
            evaluacionResponse.setEstadoRespuesta(evaluacion.getEstadoRespuesta());
            evaluacionResponse.setFechaCorrecciones(evaluacion.getFechaCorrecciones());
            evaluacionesResponse.add(evaluacionResponse);
        }

        return new ResponseEntity<>(evaluacionesResponse, HttpStatus.OK);
    }

    @PatchMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<Evaluacion> updateEvaluacion(@PathVariable Integer evaluacionId,
            @RequestBody Evaluacion evaluacion) {
        Evaluacion _evaluacion = evaluacionService.getEvaluacionById(evaluacionId);

        _evaluacion.setEstadoRespuesta(evaluacion.getEstadoRespuesta());
        _evaluacion.setFechaCorrecciones(evaluacion.getFechaCorrecciones());
        return new ResponseEntity<>(evaluacionService.save(_evaluacion), HttpStatus.CREATED);
    }

    @DeleteMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<HttpStatus> deleteEvaluacion(@PathVariable Integer evaluacionId) {
        evaluacionService.delete(evaluacionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    ////////////////
}
