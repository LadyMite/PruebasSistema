package com.teka.sistemaTEKA.sistemaTEKA.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "producto")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @NotBlank(message = "El código del producto es requerido") // Validación de no nulo y no vacío
    @Column(name = "codigo_producto", nullable = false)
    private String codigoProducto;

    @NotBlank(message = "El modelo del producto es requerido") // Validación de no nulo y no vacío
    @Column(name = "modelo", nullable = false, unique = true)
    private String modelo;

    @Column(name = "serie_producto", unique = true)
    private String serieProducto;


    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false, unique = true)
    @NotNull(message = "La categoría es requerida")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_estado_producto", nullable = false)
    private EstadoProducto estadoProducto;

}
