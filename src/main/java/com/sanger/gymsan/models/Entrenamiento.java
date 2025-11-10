package com.sanger.gymsan.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrenamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 255)
    private String descripcion;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
            name = "entrenamientos_rutina",
            joinColumns = @JoinColumn(name = "entrenamientos_id"),
            inverseJoinColumns = @JoinColumn(name = "rutinas_id"))
    @JsonBackReference("rutina-entrenamientos")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rutina> rutinas;

    @OneToMany(mappedBy = "entrenamiento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("entrenamiento-ejercicios")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<EjercicioEntrenamiento> ejerciciosEntrenamientos;
}
