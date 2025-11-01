package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.repository.EntrenamientoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class EntrenamientoService extends BaseService<Entrenamiento, Long, EntrenamientoRepository> {

}
