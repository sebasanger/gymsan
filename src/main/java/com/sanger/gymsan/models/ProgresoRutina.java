package com.sanger.gymsan.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "progresos_rutinas")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgresoRutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rutinas_id", nullable = true)
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "entrenamientos_id", nullable = true)
    private Entrenamiento entrenamiento;

    @JoinColumn(nullable = false)
    private LocalDate fecha;

    @JoinColumn(nullable = false, name = "check_in")
    private LocalDateTime checkIn;

    @JoinColumn(nullable = true, name = "check_out")
    private LocalDateTime checkOut;

}
