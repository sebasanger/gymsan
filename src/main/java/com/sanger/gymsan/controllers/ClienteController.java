package com.sanger.gymsan.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.cliente.CreateClienteDto;
import com.sanger.gymsan.dto.cliente.UpdateClienteDto;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.records.cliente.UsuarioConMembresiaActivaDTO;
import com.sanger.gymsan.services.ClienteService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    @PostMapping("")
    public ResponseEntity<?> saveCliente(@Valid @RequestBody CreateClienteDto newEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.saveCliente(newEntity, user));
    }

    @PutMapping("")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody UpdateClienteDto updateEntity,
            @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clienteService.updateCliente(updateEntity, user));
    }

}
