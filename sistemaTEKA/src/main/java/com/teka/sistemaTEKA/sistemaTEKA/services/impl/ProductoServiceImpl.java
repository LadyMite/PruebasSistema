package com.teka.sistemaTEKA.sistemaTEKA.services.impl;

import com.teka.sistemaTEKA.sistemaTEKA.models.Categoria;
import com.teka.sistemaTEKA.sistemaTEKA.models.EstadoProducto;
import com.teka.sistemaTEKA.sistemaTEKA.models.Producto;


import com.teka.sistemaTEKA.sistemaTEKA.repositories.EstadoProductoRepository;
import com.teka.sistemaTEKA.sistemaTEKA.repositories.ProductoRepository;
import com.teka.sistemaTEKA.sistemaTEKA.services.ProductoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EstadoProductoRepository estadoProductoRepository;



    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> filtrarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaNombreCategoria(categoria);
    }

    @Override
    public List<Producto> filtrarPorCodigo(String codigo) {
        return productoRepository.findByCodigoProductoContaining(codigo);
    }

    @Override
    public List<Producto> filtrarPorModelo(String modelo) {
        return productoRepository.findByModeloContaining(modelo);
    }

    @Override
    public List<Producto> filtrarPorParametros(String categoria, String codigo, String modelo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> query = cb.createQuery(Producto.class);
        Root<Producto> producto = query.from(Producto.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(categoria)) {
            predicates.add(cb.equal(producto.get("categoria").get("nombreCategoria"), categoria));
        }

        if (StringUtils.hasText(codigo)) {
            predicates.add(cb.like(producto.get("codigoProducto"), "%" + codigo + "%"));
        }

        if (StringUtils.hasText(modelo)) {
            predicates.add(cb.like(producto.get("modelo"), "%" + modelo + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public List<String> obtenerTodasLasCategorias() {
        return productoRepository.findAllCategorias();
    }


//    @Override
//    public void guardarProducto(String codigo, String modelo, Long idCategoria) {
//        Producto producto = new Producto();
//        producto.setCodigoProducto(codigo);
//        producto.setModelo(modelo);
//
//        // Obtener el estado "ACTIVO" de la base de datos
//        EstadoProducto estadoActivo = estadoProductoRepository.findById(1L)
//                .orElseThrow(() -> new EntityNotFoundException("No se encontró el estado ACTIVO"));
//        producto.setEstadoProducto(estadoActivo);
//
//        // Asignar la categoría al producto
//        Categoria categoria = new Categoria();
//        categoria.setIdCategoria(idCategoria);
//        producto.setCategoria(categoria);
//
//        // Guardar el producto en la base de datos
//        productoRepository.save(producto);
//    }

    @Override
    public void guardarProducto(String codigo, String modelo, Long idCategoria) throws Exception {

        // Verificar si ya existe un producto con el mismo código o modelo
        if (!productoRepository.findByCodigoProductoContaining(codigo).isEmpty()) {
            throw new IllegalArgumentException("El código del producto ya existe");
        }
        if (!productoRepository.findByModeloContaining(modelo).isEmpty()) {
            throw new IllegalArgumentException("El modelo del producto ya existe");
        }

        Producto producto = new Producto();
        producto.setCodigoProducto(codigo);
        producto.setModelo(modelo);

        // Obtener el estado "ACTIVO" de la base de datos
        EstadoProducto estadoActivo = estadoProductoRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el estado ACTIVO"));
        producto.setEstadoProducto(estadoActivo);

        // Asignar la categoría al producto
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        producto.setCategoria(categoria);

        // Guardar el producto en la base de datos
        productoRepository.save(producto);
    }

}