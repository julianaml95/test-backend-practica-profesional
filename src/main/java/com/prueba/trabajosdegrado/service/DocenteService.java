package com.prueba.trabajosdegrado.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.trabajosdegrado.model.Docente;
import com.prueba.trabajosdegrado.repository.DocenteRepository;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    public Docente crearDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    public List<Docente> getDocentes() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> obtenerDocentePorId(Integer id) {
        return docenteRepository.getDocenteById(id);
    }
}
