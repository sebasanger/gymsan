package com.sanger.gymsan.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.entrenamiento.CreateEntrenamientoDto;
import com.sanger.gymsan.dto.entrenamiento.UpdateEntrenamientoDto;
import com.sanger.gymsan.models.Categoria;
import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.CategoriaRepository;
import com.sanger.gymsan.repository.EjercicioRepository;
import com.sanger.gymsan.repository.EntrenamientoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class EntrenamientoService extends BaseService<Entrenamiento, Long, EntrenamientoRepository> {

    private final CategoriaRepository categoriaRepoCategoriaRepository;

    private final EjercicioRepository ejercicioRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public Entrenamiento save(CreateEntrenamientoDto newEntity, Usuario user) {
        Entrenamiento entrenamiento = new Entrenamiento();
        Categoria categoria = this.categoriaRepoCategoriaRepository.findByCategoria(newEntity.getCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));

        Set<Ejercicio> ejercicios = new HashSet<>();

        newEntity.getEjercicios().forEach(ejercicioId -> {
            Ejercicio ejercicio = this.ejercicioRepository.findById(ejercicioId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
            ejercicios.add(ejercicio);
        });

        entrenamiento.setEjercicios(ejercicios);
        entrenamiento.setNombre(newEntity.getNombre());
        entrenamiento.setDescripcion(newEntity.getDescripcion());
        entrenamiento.setCategoria(categoria);
        entrenamiento.setDeleted(false);

        return this.repository.save(entrenamiento);

    }

    public Entrenamiento update(UpdateEntrenamientoDto updateEntity, Usuario user) {
        Entrenamiento entrenamiento = this.repository.findById(updateEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product method not found"));

        Optional.ofNullable(updateEntity.getCategoria())
                .ifPresent(cat -> {
                    Categoria categoria = categoriaRepoCategoriaRepository
                            .findByCategoria(cat)
                            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
                    entrenamiento.setCategoria(categoria);
                });

        Optional.ofNullable(updateEntity.getEjercicios())
                .ifPresent(eje -> {
                    Set<Ejercicio> ejercicios = new HashSet<>();
                    updateEntity.getEjercicios().forEach(ejercicioId -> {
                        Ejercicio ejercicio = this.ejercicioRepository.findById(ejercicioId)
                                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
                        ejercicios.add(ejercicio);
                    });

                    entrenamiento.setEjercicios(ejercicios);
                });

        modelMapper.map(updateEntity, entrenamiento);

        return this.repository.save(entrenamiento);

    }
}
