package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ejercicios_entrenamientos",
            joinColumns = @JoinColumn(name = "ejercicios_id"),
            inverseJoinColumns = @JoinColumn(name = "entrenamientos_id")
    )
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Entrenamiento> entrenamientos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "fotos_ejercicio",
            joinColumns = @JoinColumn(name = "fotos_id"),
            inverseJoinColumns = @JoinColumn(name = "ejercicios_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<Foto> fotos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "videos_ejercicio",
            joinColumns = @JoinColumn(name = "videos_id"),
            inverseJoinColumns = @JoinColumn(name = "ejercicios_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<Video> videos;

}
