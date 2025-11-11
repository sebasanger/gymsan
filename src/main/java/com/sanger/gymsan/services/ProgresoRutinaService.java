package com.sanger.gymsan.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.progresoRutina.CheckOutDto;
import com.sanger.gymsan.dto.progresoRutina.CreateProgresoRutinaDto;
import com.sanger.gymsan.exceptions.MembresiaNoVigenteException;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.exceptions.UltimoCheckInNoRegistradoException;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.MembresiaRepository;
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

    public ProgresoRutina checkOut(CheckOutDto checkOutDto, Usuario user) {
        ProgresoRutina progresoRutina = this.repository.findById(checkOutDto.getProgresoRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

        progresoRutina.setCheckOut(LocalDateTime.now());

        return this.repository.save(progresoRutina);

    }

    public ProgresoRutina checkIn(Usuario user) {
        ProgresoRutina progresoRutina = new ProgresoRutina();

        MembresiaUsuario membresiaActual = this.membresiaUsuarioRepository.findByUsuarioIdAndEnabledTrue(user.getId())
                .orElseThrow(() -> new MembresiaNotEncontradaException());

        if (membresiaActual.getFechaVencimiento().isBefore(LocalDateTime.now())) {
            throw new MembresiaNoVigenteException();
        }

        Optional<ProgresoRutina> ultimoProgresoRutina = this.repository.findTopByUsuarioIdOrderByCheckInDesc(user.getId());

        if (ultimoProgresoRutina.isPresent() && ultimoProgresoRutina.get().getCheckOut() == null) {
            throw new UltimoCheckInNoRegistradoException();
        }

        progresoRutina.setUsuario(user);
        progresoRutina.setCheckIn(LocalDateTime.now());
        progresoRutina.setFecha(LocalDate.now());

        return this.repository.save(progresoRutina);

    }
}
