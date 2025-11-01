package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.repository.EjercicioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class EjercicioService extends BaseService<Ejercicio, Long, EjercicioRepository> {

}
