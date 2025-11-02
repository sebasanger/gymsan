package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.entrenamiento.CreateEntrenamientoDto;
import com.sanger.gymsan.dto.entrenamiento.UpdateEntrenamientoDto;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.EntrenamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entrenamieto")
@RequiredArgsConstructor
public class EntrenamientoController extends BaseController<Entrenamiento, Long, EntrenamientoService> {

    private final EntrenamientoService entrenamientoService;

    @PostMapping("/save")
    public ResponseEntity<?> create(@Valid @RequestBody CreateEntrenamientoDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entrenamientoService.save(newEntity, user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateEntrenamientoDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entrenamientoService.update(newEntity, user));
    }
}
