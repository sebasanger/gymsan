package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Rol;
import com.sanger.gymsan.services.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
public class RolController extends BaseController<Rol, Long, RolService> {

}
