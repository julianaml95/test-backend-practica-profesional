package com.prueba.trabajosdegrado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionResponse {
    private Integer id;
    private Integer docente;
    private Integer experto;
    private String docFormatoB;
    private String docFormatoC;
    private String docObservaciones;
    private String estadoRespuesta;
    private String fechaCorrecciones;
}