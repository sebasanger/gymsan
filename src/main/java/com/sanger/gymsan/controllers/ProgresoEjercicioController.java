package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.progresoEjercicio.AddSerieDto;
import com.sanger.gymsan.dto.progresoEjercicio.CreateProgresoEjercicioDto;
import com.sanger.gymsan.dto.progresoEjercicio.RemoveSerieDto;
import com.sanger.gymsan.dto.progresoEjercicio.UpdateProgresoEjercicioDto;
import com.sanger.gymsan.models.ProgresoEjercicio;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.ProgresoEjercicioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/progresoEjercicio")
@RequiredArgsConstructor
public class ProgresoEjercicioController extends BaseController<ProgresoEjercicio, Long, ProgresoEjercicioService> {

    private final ProgresoEjercicioService progresoEjercicioService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProgresoEjercicio(@Valid @RequestBody CreateProgresoEjercicioDto createProgresoEjercicioDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoEjercicioService.save(createProgresoEjercicioDto, user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> saveProgresoEjercicio(@Valid @RequestBody UpdateProgresoEjercicioDto updateProgresoEjercicioDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoEjercicioService.update(updateProgresoEjercicioDto, user));
    }

    @PostMapping("/addSerie")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddSerieDto addSerieDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoEjercicioService.agregarSerie(addSerieDto, user));
    }

    @DeleteMapping("/removeSerie")
    public ResponseEntity<?> removeUser(@Valid @RequestBody RemoveSerieDto removeSerieDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoEjercicioService.eliminarSerie(removeSerieDto, user));
    }

}
