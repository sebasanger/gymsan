package com.sanger.gymsan.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sanger.gymsan.dto.cliente.CreateClienteDto;
import com.sanger.gymsan.dto.cliente.UpdateClienteDto;
import com.sanger.gymsan.exceptions.UserNotFoundException;
import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.records.cliente.MembresiaActivaDTO;
import com.sanger.gymsan.records.cliente.UsuarioConMembresiaActivaDTO;
import com.sanger.gymsan.repository.MembresiaRepository;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;
import com.sanger.gymsan.repository.UserEntityRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserEntityRepository usuarioRepository;

    private final VerificationTokenService verificationTokenService;

    private final MembresiaUsuarioRepository membresiaUsuarioRepository;

    private final MembresiaRepository membresiaRepository;

    private final RolService rolService;

    @Autowired
    private final ModelMapper modelMapper;

    @Value("${base.url.frontend}")
    private String urlFrontend;

    @Value("${redirect.path.active.user}")
    private String activateUserPath;

    public Usuario saveCliente(CreateClienteDto newCliente, Usuario currentUser) {

        Usuario cliente = new Usuario();

        cliente.setDocumento(newCliente.getDocumento());
        cliente.setEmail(newCliente.getEmail());
        cliente.setUsername(newCliente.getEmail());
        cliente.setFullName(newCliente.getFullName());
        cliente.setEnabled(Boolean.FALSE);

        Set<String> roles = new HashSet<>();
        roles.add("CLIENTE");
        cliente.setRoles(rolService.getAllRolesFromStrings(roles));

        Usuario clienteSaved = usuarioRepository.save(cliente);

        Membresia membresia = this.membresiaRepository.findById(newCliente.getMembresiaId())
                .orElseThrow(() -> new EntityNotFoundException("Membresia no encontrada"));

        Optional.ofNullable(membresia)
                .ifPresent(m -> {
                    MembresiaUsuario membresiaUsuario = new MembresiaUsuario();
                    membresiaUsuario.setMembresia(membresia);
                    membresiaUsuario.setUsuario(clienteSaved);
                    membresiaUsuario.setDeleted(false);
                    membresiaUsuario.setEnabled(true);
                    membresiaUsuario.setFechaInscripcion(LocalDateTime.now());
                    this.membresiaUsuarioRepository.save(membresiaUsuario);
                });

        // verificationTokenService.sendEmailVerification(clienteSaved, this.urlFrontend
        // + "/" + this.activateUserPath);

        return clienteSaved;

    }

    public Usuario updateCliente(UpdateClienteDto updateClienteDto, Usuario currentUser) {

        try {
            Usuario cliente = this.usuarioRepository.findById(updateClienteDto.getId())
                    .orElseThrow(() -> new UserNotFoundException(updateClienteDto.getId()));

            modelMapper.map(updateClienteDto, cliente);
            Usuario usuario = this.usuarioRepository.save(cliente);

            Optional<MembresiaUsuario> activa = this.membresiaUsuarioRepository
                    .findTopByUsuarioIdAndEnabledIsTrueOrderByIdDesc(updateClienteDto.getId());

            Optional.ofNullable(updateClienteDto.getMembresiaId())
                    .ifPresent(membresiaId -> {
                        if (activa.isPresent() && !Objects.equals(activa.get().getMembresia().getId(), membresiaId)) {
                            // se desactiva la membresia anterior
                            MembresiaUsuario membresiaUsuarioAnterior = activa.get();
                            membresiaUsuarioAnterior.setEnabled(false);
                            this.membresiaUsuarioRepository.save(membresiaUsuarioAnterior);

                            // se guarda la nueva membresia
                            MembresiaUsuario newMembresiaUsuario = new MembresiaUsuario();
                            Membresia membresia = membresiaRepository
                                    .findById(membresiaId)
                                    .orElseThrow(() -> new EntityNotFoundException("Membresia no encontrada"));
                            newMembresiaUsuario.setMembresia(membresia);
                            newMembresiaUsuario.setUsuario(cliente);
                            newMembresiaUsuario.setDeleted(false);
                            newMembresiaUsuario.setEnabled(true);
                            newMembresiaUsuario.setFechaInscripcion(LocalDateTime.now());
                            this.membresiaUsuarioRepository.save(newMembresiaUsuario);
                        }
                    });

            return usuario;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no existe");
        }

    }

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
                                mu.getMembresia().getId(),
                                mu.getId(),
                                mu.getMembresia().getNombre(),
                                mu.getMembresia().getDescripcion(),
                                mu.getMembresia().getPrecio(),
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
                            u.getEnabled(),
                            membresiaDto);
                })
                .toList();
    }

    public UsuarioConMembresiaActivaDTO getById(Long id) {

        Usuario usuario = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        MembresiaActivaDTO membresiaDto = null;

        Optional<MembresiaUsuario> activa = this.membresiaUsuarioRepository
                .findTopByUsuarioIdAndEnabledIsTrueOrderByIdDesc(id);

        if (activa.isPresent()) {
            MembresiaUsuario mu = activa.get();
            membresiaDto = new MembresiaActivaDTO(
                    mu.getMembresia().getId(),
                    mu.getId(),
                    mu.getMembresia().getNombre(),
                    mu.getMembresia().getDescripcion(),
                    mu.getMembresia().getPrecio(),
                    mu.getFechaInscripcion(),
                    mu.getFechaVencimiento(),
                    mu.getFechaUltimoPago());
        }

        return new UsuarioConMembresiaActivaDTO(
                usuario.getId(),
                usuario.getFullName(),
                usuario.getDocumento(),
                usuario.getEmail(),
                usuario.getDeleted(),
                usuario.getEnabled(),
                membresiaDto);

    }

}
