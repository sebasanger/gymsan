package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.RutinaUsuario;
import com.sanger.gymsan.models.RutinasUsuariosKey;
import com.sanger.gymsan.services.RutinaUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rutinaUsuario")
@RequiredArgsConstructor
public class RutinaUsuarioController extends BaseController<RutinaUsuario, RutinasUsuariosKey, RutinaUsuarioService> {

}
