package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ejercicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "ejercicio", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("ejercicio-ejercEntrenamientos")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<EjercicioEntrenamiento> ejerciciosEntrenamientos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "fotos_ejercicio",
            joinColumns = @JoinColumn(name = "ejercicios_id"),
            inverseJoinColumns = @JoinColumn(name = "fotos_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Foto> fotos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "videos_ejercicio",
            joinColumns = @JoinColumn(name = "ejercicios_id"),
            inverseJoinColumns = @JoinColumn(name = "videos_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Video> videos;
}
