package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.services.MembresiaUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/membresiaUsuario")
@RequiredArgsConstructor
public class MembresiaUsuarioController extends BaseController<MembresiaUsuario, Long, MembresiaUsuarioService> {

}
