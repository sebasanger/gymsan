package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.ejercicio.CreateEjercicioDto;
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

    public Ejercicio save(CreateEjercicioDto newEntity, Usuario user) {
        Ejercicio ejercicio = new Ejercicio();
        Categoria categoria = this.categoriaRepoCategoriaRepository.findByCategoria(newEntity.getCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Product method not found"));

        ejercicio.setNombre(newEntity.getNombre());
        ejercicio.setDescripcion(newEntity.getDescripcion());
        ejercicio.setCategoria(categoria);

        //TODO: guardar fotos y videos
        return this.repository.save(ejercicio);

    }
}
