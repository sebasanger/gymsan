package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.services.MembresiaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/membresia")
@RequiredArgsConstructor
public class MembresiaController extends BaseController<Membresia, Long, MembresiaService> {

}
