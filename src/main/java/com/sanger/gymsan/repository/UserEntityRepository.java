package com.sanger.gymsan.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Usuario;

public interface UserEntityRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndIdNot(String email, Long id);

    Page<Usuario> findByUsernameIgnoreCaseContainingOrFullNameIgnoreCaseContaining(String username, String email,
            Pageable pageable);

}
