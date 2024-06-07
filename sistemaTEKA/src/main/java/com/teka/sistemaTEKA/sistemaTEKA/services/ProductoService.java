package com.teka.sistemaTEKA.sistemaTEKA.services;

import com.teka.sistemaTEKA.sistemaTEKA.models.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> filtrarPorCategoria(String categoria);

    List<Producto> filtrarPorCodigo(String codigo);

    List<Producto> filtrarPorModelo(String modelo);

    List<Producto> filtrarPorParametros(String categoria, String codigo, String modelo);

    List<String> obtenerTodasLasCategorias();

    //    void guardarProducto(Producto producto);
    void guardarProducto(String codigo, String modelo, Long idCategoria)  throws Exception;

}
