package com.prueba.trabajosdegrado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respuesta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "solicitud", nullable = false)
    private Integer solicitud;

    @Column(name = "docente")
    private Integer docente;

    @Column(name = "experto")
    private Integer experto;

    @Column(name = "fecha_correcciones")
    private String fecha_correcciones;
}