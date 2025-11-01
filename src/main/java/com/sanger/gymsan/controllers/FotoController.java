package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Foto;
import com.sanger.gymsan.services.FotoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/foto")
@RequiredArgsConstructor
public class FotoController extends BaseController<Foto, Long, FotoService> {

}
