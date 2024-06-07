package com.teka.sistemaTEKA.sistemaTEKA.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "categoria")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", unique = true, nullable = false)
    @NotNull(message = "La categoría es requerida")
    private String nombreCategoria;

}
