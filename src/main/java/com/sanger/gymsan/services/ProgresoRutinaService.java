package com.sanger.gymsan.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.progresoEjercicio.EjercicioEntrenamientoConProgresoDto;
import com.sanger.gymsan.dto.progresoEjercicio.EntrenamientoConProgresoDto;
import com.sanger.gymsan.dto.progresoEjercicio.RutinaActivaDto;
import com.sanger.gymsan.dto.progresoRutina.CreateProgresoRutinaDto;
import com.sanger.gymsan.exceptions.MembresiaNoVigenteException;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.exceptions.UltimoCheckInNoRegistradoException;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.ProgresoEjercicio;
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
                ProgresoRutina progresoRutina = this.repository
                                .findTopByUsuarioDocumentoAndCheckOutIsNullOrderByCheckInDesc(documento)
                                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

                progresoRutina.setCheckOut(LocalDateTime.now());

                return this.repository.save(progresoRutina);

        }

        public ProgresoRutina checkIn(String documento) {
                ProgresoRutina progresoRutina = new ProgresoRutina();

                Usuario usuario = this.usuarioService.findByDocumento(documento);

                MembresiaUsuario membresiaActual = this.membresiaUsuarioRepository
                                .findByUsuarioIdAndEnabledTrue(usuario.getId())
                                .orElseThrow(() -> new MembresiaNotEncontradaException());

                if (membresiaActual.getFechaVencimiento().isBefore(LocalDateTime.now())) {
                        throw new MembresiaNoVigenteException();
                }

                Optional<ProgresoRutina> ultimoProgresoRutina = this.repository
                                .findTopByUsuarioIdOrderByCheckInDesc(usuario.getId());

                if (ultimoProgresoRutina.isPresent() && ultimoProgresoRutina.get().getCheckOut() == null) {
                        throw new UltimoCheckInNoRegistradoException();
                }

                progresoRutina.setUsuario(usuario);
                progresoRutina.setCheckIn(LocalDateTime.now());
                progresoRutina.setFecha(LocalDate.now());

                return this.repository.save(progresoRutina);

        }

        public ProgresoRutina setRutinaAndEntrenamientoInCurrentProgreso(
                        CreateProgresoRutinaDto createProgresoEjercicioDto, Usuario user) {
                ProgresoRutina progresoRutina = this.repository
                                .findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(user.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

                Rutina rutina = this.rutinaService.findById(createProgresoEjercicioDto.getRutinaId())
                                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada"));

                Entrenamiento entrenamiento = this.entrenamientoService
                                .findById(createProgresoEjercicioDto.getEntrenamientoId())
                                .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

                progresoRutina.setRutina(rutina);
                progresoRutina.setEntrenamiento(entrenamiento);

                return this.repository.save(progresoRutina);

        }

        public RutinaActivaDto getLastActiveRoutineUser(Usuario user) {

                // 1. Obtener progreso de rutina activo
                ProgresoRutina progresoRutina = repository
                                .findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(user.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

                // 2. Obtener entrenamiento seleccionado
                Entrenamiento entrenamiento = entrenamientoService.findById(
                                progresoRutina.getEntrenamiento().getId())
                                .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

                // 3. Mapear los ejercicios del entrenamiento + su progreso
                Set<EjercicioEntrenamientoConProgresoDto> ejerciciosDto = entrenamiento.getEjerciciosEntrenamientos()
                                .stream()
                                .map(ee -> {

                                        // Buscar progreso por este Ejercicio (NO por el entrenamiento)
                                        ProgresoEjercicio progreso = progresoRutina.getProgresoEjercicio()
                                                        .stream()
                                                        .filter(p -> p.getEjercicio().getId()
                                                                        .equals(ee.getEjercicio().getId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        return EjercicioEntrenamientoConProgresoDto.builder()
                                                        .id(ee.getId())
                                                        .entrenamiento(entrenamiento)
                                                        .ejercicio(ee.getEjercicio())
                                                        .series(ee.getSeries())
                                                        .repeticiones(ee.getRepeticiones())
                                                        .peso(ee.getPeso())
                                                        .progreso(progreso)
                                                        .build();
                                })
                                .collect(Collectors.toSet());

                // 4. DTO del entrenamiento completo con sus progresos
                EntrenamientoConProgresoDto entrenamientoDto = EntrenamientoConProgresoDto.builder()
                                .id(entrenamiento.getId())
                                .nombre(entrenamiento.getNombre())
                                .deleted(entrenamiento.getDeleted())
                                .categoria(entrenamiento.getCategoria())
                                .ejercicios(ejerciciosDto)
                                .build();

                // 5. Retornar respuesta final
                return RutinaActivaDto.builder()
                                .rutina(progresoRutina.getRutina())
                                .entrenamientoSeleccionado(entrenamientoDto)
                                .fecha(progresoRutina.getFecha())
                                .checkIn(progresoRutina.getCheckIn())
                                .checkOut(progresoRutina.getCheckOut())
                                .build();
        }

}
