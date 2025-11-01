package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.repository.MembresiaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MembresiaService extends BaseService<Membresia, Long, MembresiaRepository> {

}
