package com.prueba.trabajosdegrado.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Experto;
import com.prueba.trabajosdegrado.repository.ExpertoRepository;

@Service
public class ExpertoService {
    @Autowired
    private ExpertoRepository expertoRepository;

    public Experto crearExperto(Experto experto) {
        return expertoRepository.save(experto);
    }

    public List<Experto> getExpertos() {
        return expertoRepository.findAll();
    }

    public Optional<Experto> obtenerExpertoPorId(Integer id) {
        return expertoRepository.getExpertoById(id);
    }
}
