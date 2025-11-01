package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Categoria;
import com.sanger.gymsan.repository.CategoriaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CategoriaService extends BaseService<Categoria, Long, CategoriaRepository> {

}
