package com.prueba.trabajosdegrado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluacion_id")
    private Integer evaluacionId;

    @ManyToOne
    @JoinColumn(name = "respuesta")
    private Respuesta respuesta;

    @Column(name = "doc_formato_b")
    private String docFormatoB;

    @Column(name = "doc_formato_c")
    private String docFormatoC;

    @Column(name = "doc_observaciones")
    private String docObservaciones;

    @Column(name = "estado_respuesta")
    private String estadoRespuesta;
}