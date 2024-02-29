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
@Table(name = "solicitud")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "solicitud_id")
    private Integer solicitudId;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_acta")
    private String fecha_acta;

    @Column(name = "estudiante")
    private Integer estudiante;
}
