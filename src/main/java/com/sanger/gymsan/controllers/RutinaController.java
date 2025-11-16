package com.sanger.gymsan.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.rutina.AddRemoveUserRutinaDto;
import com.sanger.gymsan.dto.rutina.CreateRutinaDto;
import com.sanger.gymsan.dto.rutina.RutinaConFlagDto;
import com.sanger.gymsan.dto.rutina.UpdateRutinaDto;
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

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRutinaDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(rutinaService.update(newEntity, user));
    }

    @PutMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddRemoveUserRutinaDto addRemoveUserRutinaDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(rutinaService.agregarUsuarioRutina(addRemoveUserRutinaDto, user));
    }

    @PutMapping("/removeUser")
    public ResponseEntity<?> removeUser(@Valid @RequestBody AddRemoveUserRutinaDto addRemoveUserRutinaDto,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(rutinaService.eliminarUsuarioRutina(addRemoveUserRutinaDto, user));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerRutinasPorUsuario(@PathVariable Long usuarioId) {
        Set<Rutina> rutinas = rutinaService.obtenerRutinasPorUsuario(usuarioId);
        return ResponseEntity.ok(rutinas);
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> obtenerRutinasPorUsuario(@AuthenticationPrincipal Usuario user) {
        Set<Rutina> rutinas = rutinaService.obtenerRutinasPorUsuario(user.getId());
        return ResponseEntity.ok(rutinas);
    }

    @GetMapping("/suscripciones")
    public List<RutinaConFlagDto> obtenerRutinasUsuario(@AuthenticationPrincipal Usuario user) {
        return rutinaService.obtenerRutinasParaUsuario(user.getId());
    }

}
