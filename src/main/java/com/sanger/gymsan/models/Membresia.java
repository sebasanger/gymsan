package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "membresias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    private Boolean activa;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "cantidad_clases")
    private String cantidadClases;

    @ManyToMany
    @JoinTable(
            name = "membresias_usuarios",
            joinColumns = @JoinColumn(name = "membresias_id"),
            inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Set<Usuario> usuarios;

}
