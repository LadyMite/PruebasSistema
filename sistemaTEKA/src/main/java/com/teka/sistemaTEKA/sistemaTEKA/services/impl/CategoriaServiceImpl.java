package com.teka.sistemaTEKA.sistemaTEKA.services.impl;

import com.teka.sistemaTEKA.sistemaTEKA.models.Categoria;
import com.teka.sistemaTEKA.sistemaTEKA.repositories.CategoriaRepository;
import com.teka.sistemaTEKA.sistemaTEKA.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
}
