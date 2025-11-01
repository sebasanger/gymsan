package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.RutinaUsuario;
import com.sanger.gymsan.models.RutinasUsuariosKey;
import com.sanger.gymsan.repository.RutinaUsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RutinaUsuarioService extends BaseService<RutinaUsuario, RutinasUsuariosKey, RutinaUsuarioRepository> {

}
