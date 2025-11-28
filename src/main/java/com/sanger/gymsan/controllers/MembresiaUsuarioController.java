package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.MembresiaUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/membresiaUsuario")
@RequiredArgsConstructor
public class MembresiaUsuarioController extends BaseController<MembresiaUsuario, Long, MembresiaUsuarioService> {

    private final MembresiaUsuarioService membresiaUsuarioService;

    @GetMapping("/getByDocumento/{documento}")
    public ResponseEntity<?> getMembresiaByDocumento(@PathVariable(required = true) String documento,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.OK).body(membresiaUsuarioService.getMembresiaByDocumento(documento));
    }

    @GetMapping("/getAllByClient/id/{id}")
    public ResponseEntity<?> getAllMembresiasUsuarioByUserId(@PathVariable(required = true) Long id,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.OK).body(membresiaUsuarioService.getAllMembresiasUsuariosByUserId(id));
    }

    @GetMapping("/getAllByClient")
    public ResponseEntity<?> getAllMembresiasUsuarioByUserId(@AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(membresiaUsuarioService.getAllMembresiasUsuariosByUserId(user.getId()));
    }
}
