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
public class RutinasUsuariosKey implements Serializable {

    @Column(name = "usuarios_id")
    private Long usuarioId;

    @Column(name = "rutinas_id")
    private Long rutinaId;
}
