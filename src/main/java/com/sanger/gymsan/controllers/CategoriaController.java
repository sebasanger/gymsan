package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Categoria;
import com.sanger.gymsan.services.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController extends BaseController<Categoria, Long, CategoriaService> {

}
