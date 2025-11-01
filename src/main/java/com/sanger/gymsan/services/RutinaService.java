package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.repository.RutinaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RutinaService extends BaseService<Rutina, Long, RutinaRepository> {

}
