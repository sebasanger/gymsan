package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MembresiaUsuarioService extends BaseService<MembresiaUsuario, Long, MembresiaUsuarioRepository> {

}
