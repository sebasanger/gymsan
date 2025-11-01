package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Foto;
import com.sanger.gymsan.repository.FotoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class FotoService extends BaseService<Foto, Long, FotoRepository> {

}
