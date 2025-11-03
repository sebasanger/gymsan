package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.rutina.CreateRutinaDto;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.RutinaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rutina")
@RequiredArgsConstructor
public class RutinaController extends BaseController<Rutina, Long, RutinaService> {

    private final RutinaService rutinaService;

    @PostMapping("/save")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRutinaDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rutinaService.save(newEntity, user));
    }

}
