package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.RutinasUsuarios;
import com.sanger.gymsan.models.RutinasUsuariosKey;

public interface RutinasUsuariosRepository extends JpaRepository<RutinasUsuarios, RutinasUsuariosKey> {

}
