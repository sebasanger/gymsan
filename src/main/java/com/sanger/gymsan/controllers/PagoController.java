package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Pago;
import com.sanger.gymsan.services.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pago")
@RequiredArgsConstructor
public class PagoController extends BaseController<Pago, Long, PagoService> {

}
