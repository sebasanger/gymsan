package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ejercicios_entrenamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EjercicioEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenamientos_id")
    @JsonBackReference("entrenamiento-ejercicios")
    private Entrenamiento entrenamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejercicios_id")
    @JsonManagedReference("ejercicio-ejercEntrenamientos")
    private Ejercicio ejercicio;

    private Integer series;
    private Integer repeticiones;
    private Double peso;

    @ManyToMany
    @JoinTable(
            name = "ejercicios_alternativos",
            joinColumns = @JoinColumn(name = "ejercicio_entrenamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "alternativo_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<EjercicioEntrenamiento> ejerciciosAlternativos;
}
