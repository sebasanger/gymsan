package com.sanger.gymsan.models;

import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "rutinas")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rutina {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nombre;

        @Enumerated(EnumType.STRING)
        @Column(name = "tipo_rutina", nullable = false)
        private TipoRutina tipoRutina;

        @Column(length = 255)
        private String descripcion;

        private Boolean deleted;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "rutinas_usuarios", joinColumns = @JoinColumn(name = "rutinas_id"), inverseJoinColumns = @JoinColumn(name = "usuarios_id"))
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        @JsonManagedReference
        @JsonIncludeProperties({ "id", "fullName", "documento" })
        private Set<Usuario> usuarios;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "entrenamientos_rutina", joinColumns = @JoinColumn(name = "rutinas_id"), inverseJoinColumns = @JoinColumn(name = "entrenamientos_id"))
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        @JsonManagedReference
        private Set<Entrenamiento> entrenamientos;

}
