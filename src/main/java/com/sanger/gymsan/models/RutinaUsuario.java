package com.sanger.gymsan.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table(name = "rutinas_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutinaUsuario {

    @EmbeddedId
    private RutinasUsuariosKey id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("rutinaId")
    @JoinColumn(name = "rutinas_id")
    private Rutina rutina;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    private Boolean activa;

}
