package com.sanger.gymsan.models;

import java.util.Set;

import java.time.LocalDateTime;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Double monto;

    private String transaction;

    private Boolean aceptada;

    @Column(length = 255)
    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "pagos_membresias",
            joinColumns = @JoinColumn(name = "pagos_id"),
            inverseJoinColumns = @JoinColumn(name = "membresias_usuarios_id")
    )
    private Set<MembresiaUsuario> membresiasUsuarios;

}
