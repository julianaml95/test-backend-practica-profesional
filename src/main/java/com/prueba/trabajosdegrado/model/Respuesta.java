package com.prueba.trabajosdegrado.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Column(name = "respuesta_id")
    private Integer respuestaId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "solicitud")
    private Integer solicitud;

    @Column(name = "docente")
    private Integer docente;

    @Column(name = "experto")
    private Integer experto;

    @OneToMany(mappedBy = "respuesta", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones;

    @Column(name = "fecha_correcciones")
    private String fecha_correcciones;
}