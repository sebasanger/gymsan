package com.sanger.gymsan.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.progresoEjercicio.EjercicioEntrenamientoConProgresoDto;
import com.sanger.gymsan.dto.progresoEjercicio.EntrenamientoConProgresoDto;
import com.sanger.gymsan.dto.progresoEjercicio.RutinaConProgresoDto;
import com.sanger.gymsan.dto.progresoRutina.CreateProgresoRutinaDto;
import com.sanger.gymsan.dto.progresos.EntrenamientoProgresoDto;
import com.sanger.gymsan.dto.progresos.ProgresoDto;
import com.sanger.gymsan.dto.progresos.ProgresoEjercicioDto;
import com.sanger.gymsan.exceptions.MembresiaNoVigenteException;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.exceptions.UltimoCheckInNoRegistradoException;
import com.sanger.gymsan.models.EjercicioEntrenamiento;
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

        public RutinaConProgresoDto getLastActiveRoutineUser(Usuario user) {

                ProgresoRutina progresoRutina = repository
                                .findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(user.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrado"));

                if (progresoRutina.getEntrenamiento() == null) {
                        return RutinaConProgresoDto.builder()
                                        .id(progresoRutina.getId())
                                        .fecha(progresoRutina.getFecha())
                                        .checkIn(progresoRutina.getCheckIn())
                                        .build();
                }

                Entrenamiento entrenamiento = entrenamientoService.findById(
                                progresoRutina.getEntrenamiento().getId())
                                .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

                Set<EjercicioEntrenamiento> ejercicios = Optional
                                .ofNullable(entrenamiento.getEjerciciosEntrenamientos())
                                .orElse(Collections.emptySet());

                // mapeo de ejercicio de la rutina con sus progresos
                Set<EjercicioEntrenamientoConProgresoDto> ejerciciosDto = ejercicios.stream()
                                .map(ee -> {

                                        ProgresoEjercicio progreso = progresoRutina.getProgresoEjercicio()
                                                        .stream()
                                                        .filter(p -> p.getEjercicio().getId()
                                                                        .equals(ee.getEjercicio().getId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        return EjercicioEntrenamientoConProgresoDto.builder()
                                                        .id(ee.getId())
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
                return RutinaConProgresoDto.builder()
                                .id(progresoRutina.getId())
                                .rutina(progresoRutina.getRutina())
                                .entrenamientoSeleccionado(entrenamientoDto)
                                .fecha(progresoRutina.getFecha())
                                .checkIn(progresoRutina.getCheckIn())
                                .checkOut(progresoRutina.getCheckOut())
                                .build();
        }

        public Set<RutinaConProgresoDto> getAllRutinasConProgreso(Usuario user) {

                // Obtener todas las rutinas del usuario con checkOut no nulo
                Set<ProgresoRutina> progresosRutinas = repository
                                .findByUsuarioIdAndCheckOutIsNotNullOrderByCheckInDesc(user.getId());

                if (progresosRutinas.isEmpty()) {
                        return Collections.emptySet();
                }

                // Iterar sobre cada ProgresoRutina y mapear a RutinaConProgresoDto
                return progresosRutinas.stream().map(progresoRutina -> {

                        // Si no tiene entrenamiento
                        if (progresoRutina.getEntrenamiento() == null) {
                                return RutinaConProgresoDto.builder()
                                                .id(progresoRutina.getId())
                                                .fecha(progresoRutina.getFecha())
                                                .checkIn(progresoRutina.getCheckIn())
                                                .checkOut(progresoRutina.getCheckOut())
                                                .build();
                        }

                        // Obtener el entrenamiento completo
                        Entrenamiento entrenamiento = entrenamientoService.findById(
                                        progresoRutina.getEntrenamiento().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

                        Set<EjercicioEntrenamiento> ejercicios = Optional
                                        .ofNullable(entrenamiento.getEjerciciosEntrenamientos())
                                        .orElse(Collections.emptySet());

                        // Mapear cada ejercicio con su progreso
                        Set<EjercicioEntrenamientoConProgresoDto> ejerciciosDto = ejercicios.stream()
                                        .map(ee -> {
                                                ProgresoEjercicio progreso = Optional
                                                                .ofNullable(progresoRutina.getProgresoEjercicio())
                                                                .orElse(Collections.emptySet())
                                                                .stream()
                                                                .filter(p -> p.getEjercicio() != null &&
                                                                                p.getEjercicio().getId().equals(ee
                                                                                                .getEjercicio()
                                                                                                .getId()))
                                                                .findFirst()
                                                                .orElse(null);

                                                return EjercicioEntrenamientoConProgresoDto.builder()
                                                                .id(ee.getId())
                                                                .ejercicio(ee.getEjercicio())
                                                                .series(ee.getSeries())
                                                                .repeticiones(ee.getRepeticiones())
                                                                .peso(ee.getPeso())
                                                                .progreso(progreso)
                                                                .build();
                                        })
                                        .collect(Collectors.toSet());

                        EntrenamientoConProgresoDto entrenamientoDto = EntrenamientoConProgresoDto.builder()
                                        .id(entrenamiento.getId())
                                        .nombre(entrenamiento.getNombre())
                                        .deleted(entrenamiento.getDeleted())
                                        .categoria(entrenamiento.getCategoria())
                                        .ejercicios(ejerciciosDto)
                                        .build();

                        // Construir DTO final para esta rutina
                        return RutinaConProgresoDto.builder()
                                        .id(progresoRutina.getId())
                                        .rutina(progresoRutina.getRutina())
                                        .entrenamientoSeleccionado(entrenamientoDto)
                                        .fecha(progresoRutina.getFecha())
                                        .checkIn(progresoRutina.getCheckIn())
                                        .checkOut(progresoRutina.getCheckOut())
                                        .build();

                }).collect(Collectors.toSet());
        }

        public long countRutinasActivas() {
                return this.repository.countByCheckOutIsNull();
        }

        public Set<Object> getAllRutinasProgresosByRutinaIdForCurrentUser(Long rutinaId, Usuario user) {

                // Obtener todas las rutinas del usuario con checkOut no nulo
                Set<ProgresoRutina> progresosRutinas = repository
                                .findByUsuarioIdAndCheckOutIsNotNullOrderByCheckInDesc(user.getId());

                if (progresosRutinas.isEmpty()) {
                        return Collections.emptySet();
                }

                // Iterar sobre cada ProgresoRutina y mapear a RutinaConProgresoDto
                return progresosRutinas.stream().map(progresoRutina -> {

                        // Si no tiene entrenamiento
                        if (progresoRutina.getEntrenamiento() == null) {
                                return ProgresoDto.builder()
                                                .id(progresoRutina.getId())
                                                .fecha(progresoRutina.getFecha())
                                                .checkIn(progresoRutina.getCheckIn())
                                                .checkOut(progresoRutina.getCheckOut())
                                                .build();
                        }

                        // Obtener el entrenamiento completo
                        Entrenamiento entrenamiento = entrenamientoService.findById(
                                        progresoRutina.getEntrenamiento().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Entrenamiento no encontrado"));

                        Set<EjercicioEntrenamiento> ejercicios = Optional
                                        .ofNullable(entrenamiento.getEjerciciosEntrenamientos())
                                        .orElse(Collections.emptySet());

                        // Mapear cada ejercicio con su progreso
                        Set<ProgresoEjercicioDto> ejerciciosDto = ejercicios.stream()
                                        .map(ee -> {
                                                ProgresoEjercicio progreso = Optional
                                                                .ofNullable(progresoRutina.getProgresoEjercicio())
                                                                .orElse(Collections.emptySet())
                                                                .stream()
                                                                .filter(p -> p.getEjercicio() != null &&
                                                                                p.getEjercicio().getId().equals(ee
                                                                                                .getEjercicio()
                                                                                                .getId()))
                                                                .findFirst()
                                                                .orElse(null);

                                                return ProgresoEjercicioDto.builder()
                                                                .id(ee.getId())
                                                                .ejercicioId(ee.getEjercicio().getId())
                                                                .nombreEjercicio(ee.getEjercicio().getNombre())
                                                                .series(ee.getSeries())
                                                                .repeticiones(ee.getRepeticiones())
                                                                .peso(ee.getPeso())
                                                                .progreso(progreso)
                                                                .build();
                                        })
                                        .collect(Collectors.toSet());

                        EntrenamientoProgresoDto entrenamientoDto = EntrenamientoProgresoDto.builder()
                                        .id(entrenamiento.getId())
                                        .nombre(entrenamiento.getNombre())
                                        .descripcion(entrenamiento.getDescripcion())
                                        .progresosEjercicios(ejerciciosDto)
                                        .build();

                        // Construir DTO final para esta rutina
                        return ProgresoDto.builder()
                                        .id(progresoRutina.getId())
                                        .rutinaId(progresoRutina.getRutina().getId())
                                        .nombreRutina(progresoRutina.getRutina().getNombre())
                                        .progresoEntrenamiento(entrenamientoDto)
                                        .fecha(progresoRutina.getFecha())
                                        .checkIn(progresoRutina.getCheckIn())
                                        .checkOut(progresoRutina.getCheckOut())
                                        .build();

                }).collect(Collectors.toSet());
        }

}
