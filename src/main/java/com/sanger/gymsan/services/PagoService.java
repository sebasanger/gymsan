package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Pago;
import com.sanger.gymsan.repository.PagoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PagoService extends BaseService<Pago, Long, PagoRepository> {

}
