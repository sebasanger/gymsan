package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.services.EntrenamientoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entrenamiento")
@RequiredArgsConstructor
public class EntrenamientoController extends BaseController<Entrenamiento, Long, EntrenamientoService> {

}
