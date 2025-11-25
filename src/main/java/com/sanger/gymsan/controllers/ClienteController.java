package com.sanger.gymsan.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.records.cliente.UsuarioConMembresiaActivaDTO;
import com.sanger.gymsan.services.ClienteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") boolean includeDeleted) {
        List<UsuarioConMembresiaActivaDTO> result = clienteService.listarClientesConMembresiaActiva(includeDeleted);

        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

}
