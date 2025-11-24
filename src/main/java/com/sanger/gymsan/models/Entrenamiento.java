package com.sanger.gymsan.models;

import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sanger.gymsan.services.SoftDeletableInterface;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrenamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE entrenamientos SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Entrenamiento implements SoftDeletableInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 255)
    private String descripcion;

    private Boolean deleted;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(name = "entrenamientos_rutina", joinColumns = @JoinColumn(name = "entrenamientos_id"), inverseJoinColumns = @JoinColumn(name = "rutinas_id"))
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
