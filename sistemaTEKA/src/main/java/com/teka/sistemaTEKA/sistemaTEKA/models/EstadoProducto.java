package com.teka.sistemaTEKA.sistemaTEKA.models;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "estadoproducto")
@Data
public class EstadoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_producto")
    private Long idEstadoProducto;

    @Column(name = "nombre_estado_producto", unique = true, nullable = false)
    private String nombreEstadoProducto;



}
