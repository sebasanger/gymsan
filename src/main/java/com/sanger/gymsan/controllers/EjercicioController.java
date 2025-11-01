package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.services.EjercicioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ejercicio")
@RequiredArgsConstructor
public class EjercicioController extends BaseController<Ejercicio, Long, EjercicioService> {

}
