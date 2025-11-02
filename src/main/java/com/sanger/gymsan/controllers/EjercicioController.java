package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.ejercicio.CreateEjercicioDto;
import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.EjercicioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ejercicio")
@RequiredArgsConstructor
public class EjercicioController extends BaseController<Ejercicio, Long, EjercicioService> {

    private final EjercicioService ejercicioService;

    @PostMapping("/save")
    public ResponseEntity<?> create(@Valid @RequestBody CreateEjercicioDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioService.save(newEntity, user));
    }

}
