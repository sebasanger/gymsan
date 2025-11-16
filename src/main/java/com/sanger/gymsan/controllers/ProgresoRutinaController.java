package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.progresoRutina.CreateProgresoRutinaDto;
import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.ProgresoRutinaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/progresoRutina")
@RequiredArgsConstructor
public class ProgresoRutinaController extends BaseController<ProgresoRutina, Long, ProgresoRutinaService> {

    private final ProgresoRutinaService progresoRutinaService;

    @PostMapping("/save")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateProgresoRutinaDto createProgresoRutinaDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(progresoRutinaService.save(createProgresoRutinaDto, user));
    }

    @PutMapping("/checkOut")
    public ResponseEntity<?> checkOut(@Valid @RequestBody String documento,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoRutinaService.checkOut(documento));
    }

    @PostMapping("/checkIn")
    public ResponseEntity<?> checkIn(@Valid @RequestBody String documento, @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoRutinaService.checkIn(documento));
    }

    @PutMapping("/guardarRutinaEntrenamiento")
    public ResponseEntity<?> checkOut(@Valid @RequestBody CreateProgresoRutinaDto createProgresoRutinaDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(progresoRutinaService.setRutinaAndEntrenamientoInCurrentProgreso(createProgresoRutinaDto, user));
    }

    @GetMapping("/last/active")
    public ResponseEntity<?> getLastActiveRoutineUser(@AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoRutinaService.getLastActiveRoutineUser(user));
    }

    @GetMapping("/own")
    public ResponseEntity<?> getOwnProgresosRutinas(@AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(progresoRutinaService.getAllRutinasConProgreso(user));
    }

    @GetMapping("/activas/count")
    public ResponseEntity<Long> getCantidadRutinasActivas() {
        return ResponseEntity.ok(progresoRutinaService.countRutinasActivas());
    }
}
