package com.sanger.gymsan.services;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.records.cliente.MembresiaActivaDTO;
import com.sanger.gymsan.records.cliente.UsuarioConMembresiaActivaDTO;
import com.sanger.gymsan.repository.UserEntityRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserEntityRepository usuarioRepository;

    public List<UsuarioConMembresiaActivaDTO> listarClientesConMembresiaActiva(Boolean includeDeleted) {

        Session session = entityManager.unwrap(Session.class);

        if (!includeDeleted) {
            session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        } else {
            session.disableFilter("deletedFilter");
        }
        List<Object[]> filas = usuarioRepository.findUsuariosClienteConMembresiaActiva();

        return filas.stream()
                .map(f -> {
                    Usuario u = (Usuario) f[0];
                    MembresiaUsuario mu = (MembresiaUsuario) f[1];

                    MembresiaActivaDTO membresiaDto = null;

                    if (mu != null) {
                        membresiaDto = new MembresiaActivaDTO(
                                mu.getId(),
                                mu.getMembresia().getNombre(),
                                mu.getFechaInscripcion(),
                                mu.getFechaVencimiento(),
                                mu.getFechaUltimoPago());
                    }

                    return new UsuarioConMembresiaActivaDTO(
                            u.getId(),
                            u.getFullName(),
                            u.getDocumento(),
                            u.getEmail(),
                            u.getDeleted(),
                            membresiaDto);
                })
                .toList();
    }

}
