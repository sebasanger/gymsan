package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.EjercicioEntrenamiento;
import com.sanger.gymsan.repository.EjercicioEntrenamientoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class EjercicioEntrenamientoService extends BaseService<EjercicioEntrenamiento, Long, EjercicioEntrenamientoRepository> {

}
