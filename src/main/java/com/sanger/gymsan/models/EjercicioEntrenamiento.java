package com.sanger.gymsan.models;

import java.util.Set;

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
@Table(name = "ejercicios_entrenamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EjercicioEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entrenamientos_id")
    private Entrenamiento entrenamiento;

    @ManyToOne
    @JoinColumn(name = "ejercicios_id")
    private Ejercicio ejercicio;

    private Integer series;
    private Integer repeticiones;
    private Double peso;

    @ManyToMany
    @JoinTable(
            name = "ejercicios_alternativos",
            joinColumns = @JoinColumn(name = "ejercicio_entrenamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "alternativo_id")
    )
    private Set<EjercicioEntrenamiento> ejerciciosAlternativos;

}
