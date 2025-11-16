package com.sanger.gymsan.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.rutina.CreateRutinaDto;
import com.sanger.gymsan.dto.rutina.RutinaConFlagDto;
import com.sanger.gymsan.dto.rutina.UpdateRutinaDto;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.TipoRutina;
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

    public Rutina save(CreateRutinaDto newEntity, Usuario user, Boolean createForAuthUser) {
        Rutina rutina = new Rutina();

        Set<Usuario> usuarioDestinoList = new HashSet<>();
        if (createForAuthUser) {
            usuarioDestinoList.add(user);
        } else {
            newEntity.getUsuariosId().forEach(us -> {
                Usuario usuario = this.usuarioService.findById(us)
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
                usuarioDestinoList.add(usuario);
            });
        }

        Set<Entrenamiento> entrenamientos = new HashSet<>();

        newEntity.getEntrenamientos().forEach(entr -> {
            Entrenamiento entrenamiento = this.entrenamientoService.save(entr, user);
            entrenamientos.add(entrenamiento);
        });

        rutina.setTipoRutina(newEntity.getTipoRutina());
        rutina.setUsuarios(usuarioDestinoList);
        rutina.setEntrenamientos(entrenamientos);
        rutina.setNombre(newEntity.getNombre());
        rutina.setDescripcion(newEntity.getDescripcion());
        rutina.setDeleted(false);

        return this.repository.save(rutina);

    }

    public Rutina update(UpdateRutinaDto updateEntity, Usuario user, Boolean createForAuthUser) {
        Rutina rutina = this.repository.findById(updateEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        Set<Usuario> usuarioDestinoList = new HashSet<>();
        if (createForAuthUser) {
            usuarioDestinoList.add(user);
        } else {
            updateEntity.getUsuariosId().forEach(us -> {
                Usuario usuario = this.usuarioService.findById(us)
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
                usuarioDestinoList.add(usuario);
            });
        }

        Set<Entrenamiento> entrenamientos = new HashSet<>();

        updateEntity.getEntrenamientos().forEach(entr -> {

            Entrenamiento entrenamiento = this.entrenamientoService.save(entr, user);
            entrenamientos.add(entrenamiento);
        });

        rutina.setTipoRutina(updateEntity.getTipoRutina());
        rutina.setUsuarios(usuarioDestinoList);
        rutina.setEntrenamientos(entrenamientos);
        rutina.setNombre(updateEntity.getNombre());
        rutina.setDescripcion(updateEntity.getDescripcion());
        rutina.setDeleted(false);

        return this.repository.save(rutina);

    }

    public Rutina agregarUsuarioRutina(Long rutinaId, Long userId) {

        Usuario usuario = this.usuarioService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rutina rutina = this.repository.findById(rutinaId)
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        rutina.getUsuarios().add(usuario);

        return this.repository.save(rutina);

    }

    public Rutina eliminarUsuarioRutina(Long rutinaId, Long userId) {

        Usuario usuario = this.usuarioService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rutina rutina = this.repository.findById(rutinaId)
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrado"));

        rutina.getUsuarios().remove(usuario);

        return this.repository.save(rutina);

    }

    public Set<Rutina> obtenerRutinasPorUsuario(Long usuarioId) {
        return this.repository.findByUsuarios_Id(usuarioId);
    }

    public List<RutinaConFlagDto> obtenerRutinasParaUsuario(Long usuarioId) {

        // Rutinas a las que ya está suscripto
        Set<Rutina> suscriptas = this.repository.findByUsuarios_Id(usuarioId);

        // Rutinas predeterminadas del sistema
        Set<Rutina> predeterminadas = this.repository.findByTipoRutina(TipoRutina.PREDETERMINADA);

        // Mapear a DTO y evitar duplicados
        Set<Long> idsSuscriptas = suscriptas.stream()
                .map(Rutina::getId)
                .collect(Collectors.toSet());

        List<RutinaConFlagDto> resultado = new ArrayList<>();

        // Primero las suscriptas (marcadas)
        resultado.addAll(
                suscriptas.stream()
                        .map(r -> RutinaConFlagDto.fromEntity(r, true))
                        .toList());

        // Luego las predeterminadas NO suscriptas
        resultado.addAll(
                predeterminadas.stream()
                        .filter(r -> !idsSuscriptas.contains(r.getId()))
                        .map(r -> RutinaConFlagDto.fromEntity(r, false))
                        .toList());

        return resultado;
    }

}
