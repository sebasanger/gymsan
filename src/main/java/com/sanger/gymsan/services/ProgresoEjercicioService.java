package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.ProgresoEjercicio;
import com.sanger.gymsan.repository.ProgresoEjercicioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProgresoEjercicioService extends BaseService<ProgresoEjercicio, Long, ProgresoEjercicioRepository> {

}
