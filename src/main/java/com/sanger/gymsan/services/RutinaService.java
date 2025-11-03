package com.sanger.gymsan.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.rutina.CreateRutinaDto;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.RutinaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RutinaService extends BaseService<Rutina, Long, RutinaRepository> {

    private final UsuarioService usuarioService;

    private final EntrenamientoService entrenamientoService;

    public Rutina save(CreateRutinaDto newEntity, Usuario user) {
        Rutina rutina = new Rutina();

        Usuario usuarioDestino = this.usuarioService.findById(newEntity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Set<Usuario> usuarioDestinoList = new HashSet<>();
        usuarioDestinoList.add(usuarioDestino);

        Set<Entrenamiento> entrenamientos = new HashSet<>();

        newEntity.getEntrenamientos().forEach(entrenamientoDto -> {
            Entrenamiento entrenamiento = this.entrenamientoService.save(entrenamientoDto, user);
            entrenamientos.add(entrenamiento);
        });

        rutina.setUsuarios(usuarioDestinoList);
        rutina.setEntrenamientos(entrenamientos);
        //rutina.setCreador(user);
        rutina.setNombre(newEntity.getNombre());
        rutina.setDescripcion(newEntity.getDescripcion());
        rutina.setDeleted(false);

        return this.repository.save(rutina);

    }

}
