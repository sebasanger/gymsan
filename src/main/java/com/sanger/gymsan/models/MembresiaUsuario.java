package com.sanger.gymsan.models;

import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "membresias_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembresiaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "membresias_id")
    private Membresia membresia;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    private Boolean enabled;

    private Boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "pagos_membresias",
            joinColumns = @JoinColumn(name = "pagos_id"),
            inverseJoinColumns = @JoinColumn(name = "membresias_usuarios_id")
    )
    private Set<Pago> pagos;

}
