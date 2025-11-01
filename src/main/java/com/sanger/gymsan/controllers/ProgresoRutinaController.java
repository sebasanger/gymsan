package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.services.ProgresoRutinaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/progresoRutina")
@RequiredArgsConstructor
public class ProgresoRutinaController extends BaseController<ProgresoRutina, Long, ProgresoRutinaService> {

}
