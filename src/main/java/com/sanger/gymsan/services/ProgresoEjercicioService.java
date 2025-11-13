package com.sanger.gymsan.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.progresoEjercicio.AddSerieDto;
import com.sanger.gymsan.dto.progresoEjercicio.CreateProgresoEjercicioDto;
import com.sanger.gymsan.dto.progresoEjercicio.RemoveSerieDto;
import com.sanger.gymsan.dto.progresoEjercicio.UpdateProgresoEjercicioDto;
import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.models.ProgresoEjercicio;
import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.models.Serie;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.ProgresoEjercicioRepository;
import com.sanger.gymsan.repository.SerieRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProgresoEjercicioService extends BaseService<ProgresoEjercicio, Long, ProgresoEjercicioRepository> {

    private final ProgresoRutinaService progresoRutinaService;

    private final EjercicioService ejercicioService;

    private final SerieRepository serieRepository;

    public ProgresoEjercicio save(CreateProgresoEjercicioDto newEntity, Usuario user) {
        ProgresoEjercicio progresoEjercicio = new ProgresoEjercicio();

        Ejercicio ejercicio = this.ejercicioService.findById(newEntity.getEjercicioId())
                .orElseThrow(() -> new EntityNotFoundException("Ejercicio no encontrado"));

        ProgresoRutina progresoRutina = this.progresoRutinaService.findById(newEntity.getProgresoRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrada"));

        Set<Serie> series = new HashSet<>();

        newEntity.getSeries().forEach(serie -> {
            series.add(this.serieRepository.save(serie));
        });

        progresoEjercicio.setEjercicio(ejercicio);
        progresoEjercicio.setProgresoRutina(progresoRutina);
        progresoEjercicio.setSeries(series);

        return this.repository.save(progresoEjercicio);

    }

    public ProgresoEjercicio agregarSerie(AddSerieDto addSerieDto, Usuario user) {
        ProgresoEjercicio progresoEjercicio = this.repository.findById(addSerieDto.getProgresoEjercicioId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso ejercicio no encontrado"));

        Serie newSerie = new Serie();
        newSerie.setPeso(addSerieDto.getPeso());
        newSerie.setRepeticiones(addSerieDto.getRepeticiones());

        Serie serie = this.serieRepository.save(newSerie);

        progresoEjercicio.getSeries().add(serie);

        return this.repository.save(progresoEjercicio);
    }

    public ProgresoEjercicio eliminarSerie(RemoveSerieDto addRemoveSerieDto, Usuario user) {
        ProgresoEjercicio progresoEjercicio = this.repository.findById(addRemoveSerieDto.getProgresoEjercicioId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso ejercicio no encontrado"));

        Serie serie = this.serieRepository.findById(addRemoveSerieDto.getSerieId())
                .orElseThrow(() -> new EntityNotFoundException("Serie no encontrada"));

        progresoEjercicio.getSeries().remove(serie);

        this.serieRepository.delete(serie);

        return this.repository.save(progresoEjercicio);
    }

    public ProgresoEjercicio update(UpdateProgresoEjercicioDto updateEntity, Usuario user) {
        ProgresoEjercicio progresoEjercicio = this.repository.findById(updateEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ejercicio no encontrado"));

        Ejercicio ejercicio = this.ejercicioService.findById(updateEntity.getEjercicioId())
                .orElseThrow(() -> new EntityNotFoundException("Ejercicio no encontrado"));

        ProgresoRutina progresoRutina = this.progresoRutinaService.findById(updateEntity.getProgresoRutinaId())
                .orElseThrow(() -> new EntityNotFoundException("Progreso rutina no encontrada"));

        Set<Serie> series = new HashSet<>();

        updateEntity.getSeries().forEach(serie -> {
            series.add(this.serieRepository.save(serie));
        });

        progresoEjercicio.setEjercicio(ejercicio);
        progresoEjercicio.setProgresoRutina(progresoRutina);
        progresoEjercicio.setSeries(series);

        return this.repository.save(progresoEjercicio);

    }

}
