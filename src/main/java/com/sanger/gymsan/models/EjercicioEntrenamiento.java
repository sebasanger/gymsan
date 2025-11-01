package com.sanger.gymsan.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    @EmbeddedId
    private EjercicioEntrenamientoKey id;

    @ManyToOne
    @MapsId("entrenamientoId")
    @JoinColumn(name = "entrenamientos_id")
    private Entrenamiento entrenamiento;

    @ManyToOne
    @MapsId("ejercicioId")
    @JoinColumn(name = "ejercicios_id")
    private Ejercicio ejercicio;

    private Integer series;
    private Integer repeticiones;
    private Double peso;

}
