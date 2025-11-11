package com.sanger.gymsan.models;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JsonManagedReference
    @JsonIncludeProperties({"id", "fullName", "documento"})
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "membresias_id")
    @JsonManagedReference
    private Membresia membresia;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    private Boolean enabled;

    private Boolean deleted;

    @ManyToMany(mappedBy = "membresiasUsuarios")
    @JsonIgnore
    private Set<Pago> pagos;

}
