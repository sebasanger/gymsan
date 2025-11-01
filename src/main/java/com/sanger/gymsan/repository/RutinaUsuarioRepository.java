package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.RutinaUsuario;
import com.sanger.gymsan.models.RutinasUsuariosKey;

public interface RutinaUsuarioRepository extends JpaRepository<RutinaUsuario, RutinasUsuariosKey> {

}
