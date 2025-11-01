package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.ProgresoEjercicio;
import com.sanger.gymsan.services.ProgresoEjercicioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/progresoEjercicio")
@RequiredArgsConstructor
public class ProgresoEjercicioController extends BaseController<ProgresoEjercicio, Long, ProgresoEjercicioService> {

}
