package com.sanger.gymsan.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjercicioEntrenamientoKey implements Serializable {

    @Column(name = "entrenamientos_id")
    private Long entrenamientoId;

    @Column(name = "ejercicios_id")
    private Long ejercicioId;
}
