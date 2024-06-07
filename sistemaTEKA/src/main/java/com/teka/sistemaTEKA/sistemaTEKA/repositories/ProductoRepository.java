package com.teka.sistemaTEKA.sistemaTEKA.repositories;

import com.teka.sistemaTEKA.sistemaTEKA.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT DISTINCT c.nombreCategoria FROM Producto p JOIN p.categoria c")
    List<String> findAllCategorias();

    List<Producto> findByCategoriaNombreCategoria(String categoria);

    List<Producto> findByCodigoProductoContaining(String codigo);

    List<Producto> findByModeloContaining(String modelo);

    List<Producto> findByCategoriaNombreCategoriaAndCodigoProductoContaining(String categoria, String codigo);

    List<Producto> findByCategoriaNombreCategoriaAndModeloContaining(String categoria, String modelo);

    List<Producto> findByCodigoProductoContainingAndModeloContaining(String codigo, String modelo);

    List<Producto> findByCategoriaNombreCategoriaAndCodigoProductoContainingAndModeloContaining(String categoria, String codigo, String modelo);


    // Métodos adicionales para validaciones
    Optional<Producto> findByCodigoProducto(String codigoProducto);

    Optional<Producto> findByModelo(String modelo);


}
