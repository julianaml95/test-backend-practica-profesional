package com.prueba.trabajosdegrado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.trabajosdegrado.model.Experto;

public interface ExpertoRepository extends JpaRepository<Experto, Integer> {
    @Query("SELECT d FROM Experto d WHERE d.id = ?1")
    Optional<Experto> getExpertoById(Integer id);
}
