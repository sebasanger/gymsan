package com.sanger.gymsan.models;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sanger.gymsan.services.SoftDeletableInterface;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@SQLDelete(sql = "UPDATE membresias_usuarios SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class MembresiaUsuario implements SoftDeletableInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    @JsonManagedReference
    @JsonIncludeProperties({ "id", "fullName", "documento" })
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "membresias_id")
    @JsonManagedReference
    private Membresia membresia;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Column(name = "fecha_ultimo_pago")
    private LocalDateTime fechaUltimoPago;

    private Boolean enabled;

    private Boolean deleted;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(mappedBy = "membresiaUsuario")
    @JsonBackReference
    @JsonIgnore
    private Set<Pago> pagos;

}
