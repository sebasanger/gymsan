package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrenamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 255)
    private String descripcion;

    private Boolean activa;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
            name = "entrenamientos_rutina",
            joinColumns = @JoinColumn(name = "entrenamientos_id"),
            inverseJoinColumns = @JoinColumn(name = "rutinas_id"))

    @JsonIgnore
    private Set<Rutina> rutinas;

}
