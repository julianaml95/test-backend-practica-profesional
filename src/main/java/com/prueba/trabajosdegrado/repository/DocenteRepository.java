package com.prueba.trabajosdegrado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    @Query("SELECT d FROM Docente d WHERE d.id = ?1")
    Optional<Docente> getDocenteById(Integer id);
}
