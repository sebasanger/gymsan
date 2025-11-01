package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.services.RutinaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rutina")
@RequiredArgsConstructor
public class RutinaController extends BaseController<Rutina, Long, RutinaService> {

}
