package com.sanger.gymsan.services;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.rutina.AddRemoveUserRutinaDto;
import com.sanger.gymsan.dto.rutina.CreateRutinaDto;
import com.sanger.gymsan.dto.rutina.UpdateRutinaDto;
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

        Set<Usuario> usuarioDestinoList = new HashSet<>();
        newEntity.getUsuariosId().forEach(us -> {

            Usuario usuario = this.usuarioService.findById(us)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            usuarioDestinoList.add(usuario);
        });

        Set<Entrenamiento> entrenamientos = new HashSet<>();

        newEntity.getEntrenamientos().forEach(entr -> {

            Entrenamiento entrenamiento = this.entrenamientoService.save(entr, user);
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

    public Rutina update(UpdateRutinaDto updateEntity, Usuario user) {
        Rutina rutina = this.repository.findById(updateEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        Usuario usuarioDestino = this.usuarioService.findById(updateEntity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Set<Usuario> usuarioDestinoList = new HashSet<>();
        usuarioDestinoList.add(usuarioDestino);

        Set<Entrenamiento> entrenamientos = new HashSet<>();

        updateEntity.getEntrenamientos().forEach(entr -> {

            Entrenamiento entrenamiento = this.entrenamientoService.save(entr, user);
            entrenamientos.add(entrenamiento);
        });

        rutina.setUsuarios(usuarioDestinoList);
        rutina.setEntrenamientos(entrenamientos);
        //rutina.setCreador(user);
        rutina.setNombre(updateEntity.getNombre());
        rutina.setDescripcion(updateEntity.getDescripcion());
        rutina.setDeleted(false);

        return this.repository.save(rutina);

    }

    public Rutina agregarUsuarioRutina(AddRemoveUserRutinaDto addRemoveUserRutinaDto, Usuario user) {

        Usuario usuario = this.usuarioService.findById(addRemoveUserRutinaDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rutina rutina = this.repository.findById(addRemoveUserRutinaDto.getRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        rutina.getUsuarios().add(usuario);

        return this.repository.save(rutina);

    }

    public Rutina eliminarUsuarioRutina(AddRemoveUserRutinaDto addRemoveUserRutinaDto, Usuario user) {

        Usuario usuario = this.usuarioService.findById(addRemoveUserRutinaDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rutina rutina = this.repository.findById(addRemoveUserRutinaDto.getRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        rutina.getUsuarios().remove(usuario);

        return this.repository.save(rutina);

    }

}
