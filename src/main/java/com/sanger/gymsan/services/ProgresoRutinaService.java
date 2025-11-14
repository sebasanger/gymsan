package com.sanger.gymsan.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.progresoRutina.CreateProgresoRutinaDto;
import com.sanger.gymsan.exceptions.MembresiaNoVigenteException;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.exceptions.UltimoCheckInNoRegistradoException;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;
import com.sanger.gymsan.repository.ProgresoRutinaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProgresoRutinaService extends BaseService<ProgresoRutina, Long, ProgresoRutinaRepository> {

    private final UsuarioService usuarioService;

    private final RutinaService rutinaService;

    private final EntrenamientoService entrenamientoService;

    private final MembresiaUsuarioRepository membresiaUsuarioRepository;

    public ProgresoRutina save(CreateProgresoRutinaDto newEntity, Usuario user) {
        ProgresoRutina progresoRutina = new ProgresoRutina();

        Usuario usuarioActual = this.usuarioService.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rutina rutina = this.rutinaService.findById(newEntity.getRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada"));

        Entrenamiento entrenamiento = this.entrenamientoService.findById(newEntity.getEntrenamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

        progresoRutina.setUsuario(usuarioActual);
        progresoRutina.setEntrenamiento(entrenamiento);
        progresoRutina.setRutina(rutina);
        progresoRutina.setCheckIn(LocalDateTime.now());
        progresoRutina.setFecha(LocalDate.now());

        return this.repository.save(progresoRutina);

    }

    public ProgresoRutina checkOut(String documento) {
        ProgresoRutina progresoRutina = this.repository.findTopByUsuarioDocumentoAndCheckOutIsNullOrderByCheckInDesc(documento)
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

        progresoRutina.setCheckOut(LocalDateTime.now());

        return this.repository.save(progresoRutina);

    }

    public ProgresoRutina checkIn(String documento) {
        ProgresoRutina progresoRutina = new ProgresoRutina();

        Usuario usuario = this.usuarioService.findByDocumento(documento);

        MembresiaUsuario membresiaActual = this.membresiaUsuarioRepository.findByUsuarioIdAndEnabledTrue(usuario.getId())
                .orElseThrow(() -> new MembresiaNotEncontradaException());

        if (membresiaActual.getFechaVencimiento().isBefore(LocalDateTime.now())) {
            throw new MembresiaNoVigenteException();
        }

        Optional<ProgresoRutina> ultimoProgresoRutina = this.repository.findTopByUsuarioIdOrderByCheckInDesc(usuario.getId());

        if (ultimoProgresoRutina.isPresent() && ultimoProgresoRutina.get().getCheckOut() == null) {
            throw new UltimoCheckInNoRegistradoException();
        }

        progresoRutina.setUsuario(usuario);
        progresoRutina.setCheckIn(LocalDateTime.now());
        progresoRutina.setFecha(LocalDate.now());

        return this.repository.save(progresoRutina);

    }

    public ProgresoRutina setRutinaAndEntrenamientoInCurrentProgreso(CreateProgresoRutinaDto createProgresoEjercicioDto, Usuario user) {
        ProgresoRutina progresoRutina = this.repository.findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

        Rutina rutina = this.rutinaService.findById(createProgresoEjercicioDto.getRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada"));

        Entrenamiento entrenamiento = this.entrenamientoService.findById(createProgresoEjercicioDto.getEntrenamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

        progresoRutina.setRutina(rutina);
        progresoRutina.setEntrenamiento(entrenamiento);

        return this.repository.save(progresoRutina);

    }

    public ProgresoRutina getLastActiveRoutineUser(Usuario user) {
        return this.repository.findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

    }
}
