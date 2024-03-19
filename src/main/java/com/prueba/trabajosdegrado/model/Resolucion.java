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
@Table(name = "resolucion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "solicitud", nullable = false)
    private Integer solicitud;

    @Column(name = "director")
    private Integer director;

    @Column(name = "codirector")
    private Integer codirector;

    @Column(name = "numero_acta_revision")
    private Integer numeroActaRevision;

    @Column(name = "numero_resolucion")
    private Integer numeroResolucion;

    @Column(name = "anteproyecto_aprobado")
    private String anteproyectoAprobado;

    @Column(name = "solicitud_comite")
    private String solicitudComite;

    @Column(name = "solicitud_concejo")
    private String solicitudConcejo;

    @Column(name = "resolucion_concejo")
    private String resolucionConcejo;

    @Column(name = "fecha_acta")
    private String fechaActa;

    @Column(name = "fecha_resolucion")
    private String fechaResolucion;
}