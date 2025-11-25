package com.sanger.gymsan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sanger.gymsan.models.Usuario;

public interface UserEntityRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDocumento(String documento);

    Optional<Usuario> findByEmailAndIdNot(String email, Long id);

    Page<Usuario> findByUsernameIgnoreCaseContainingOrFullNameIgnoreCaseContaining(String username, String email,
            Pageable pageable);

    List<Usuario> findByRoles_Rol(String rol);

    List<Usuario> findByRoles_RolIn(List<String> roles);

    @Query("select u from Usuario u join u.roles r where r.rol in :roles")
    List<Usuario> findUsuariosByRoles(@Param("roles") List<String> roles);

    @Query("""
                select u, mu
                from Usuario u
                join u.roles r
                left join u.membresiasUsuarios mu on mu.enabled = true
                where r.rol = 'CLIENTE'
            """)
    List<Object[]> findUsuariosClienteConMembresiaActiva();

}
