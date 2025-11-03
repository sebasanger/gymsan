package com.sanger.gymsan.models;

import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "progresos_ejercicios")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgresoEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad_series")
    private Integer cantidadSeries;

    @ManyToOne
    @JoinColumn(name = "progresos_rutinas_id", nullable = false)
    private ProgresoEjercicio usuario;

    @ManyToOne
    @JoinColumn(name = "ejercicios_id", nullable = false)
    private Ejercicio ejercicio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "series_progresos_rutinas",
            joinColumns = @JoinColumn(name = "progresos_ejercicios_id"),
            inverseJoinColumns = @JoinColumn(name = "series_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<Serie> series;
}
