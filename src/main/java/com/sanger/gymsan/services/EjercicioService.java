package com.sanger.gymsan.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.ejercicio.CreateEjercicioDto;
import com.sanger.gymsan.dto.ejercicio.UpdateEjercicioDto;
import com.sanger.gymsan.models.Categoria;
import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.CategoriaRepository;
import com.sanger.gymsan.repository.EjercicioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class EjercicioService extends BaseService<Ejercicio, Long, EjercicioRepository> {

    private final CategoriaRepository categoriaRepoCategoriaRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public Ejercicio save(CreateEjercicioDto newEntity, Usuario user) {
        Ejercicio ejercicio = new Ejercicio();
        Categoria categoria = this.categoriaRepoCategoriaRepository.findByCategoria(newEntity.getCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));

        ejercicio.setNombre(newEntity.getNombre());
        ejercicio.setDescripcion(newEntity.getDescripcion());
        ejercicio.setCategoria(categoria);

        //TODO: guardar fotos y videos
        return this.repository.save(ejercicio);

    }

    public Ejercicio update(UpdateEjercicioDto updateEntity, Usuario user) {
        Ejercicio ejercicio = this.repository.findById(updateEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ejercicio no encontrado"));

        Optional.ofNullable(updateEntity.getCategoria())
                .ifPresent(cat -> {
                    Categoria categoria = categoriaRepoCategoriaRepository
                            .findByCategoria(cat)
                            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
                    ejercicio.setCategoria(categoria);
                });

        modelMapper.map(updateEntity, ejercicio);

        return this.repository.save(ejercicio);

    }
}
