package com.sanger.gymsan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRol(String rol);
}
