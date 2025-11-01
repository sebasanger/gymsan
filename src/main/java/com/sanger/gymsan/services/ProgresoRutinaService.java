package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.ProgresoRutina;
import com.sanger.gymsan.repository.ProgresoRutinaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProgresoRutinaService extends BaseService<ProgresoRutina, Long, ProgresoRutinaRepository> {

}
