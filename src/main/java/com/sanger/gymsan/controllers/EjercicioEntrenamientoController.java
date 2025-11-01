package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.EjercicioEntrenamiento;
import com.sanger.gymsan.services.EjercicioEntrenamientoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ejercicioEntrenamiento")
@RequiredArgsConstructor
public class EjercicioEntrenamientoController extends BaseController<EjercicioEntrenamiento, Long, EjercicioEntrenamientoService> {

}
