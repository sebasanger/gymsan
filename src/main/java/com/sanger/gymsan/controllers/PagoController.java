package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Pago;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pago")
@RequiredArgsConstructor
public class PagoController extends BaseController<Pago, Long, PagoService> {

    private final PagoService pagoService;

    @GetMapping("/membresiaUsuario/{id}")
    public ResponseEntity<?> getAllMembresiasUsuarioByUserId(@PathVariable(required = true) Long id,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.OK).body(pagoService.getPagosByMembresiaId(id));
    }
}
