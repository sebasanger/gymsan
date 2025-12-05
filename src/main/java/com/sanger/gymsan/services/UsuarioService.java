package com.sanger.gymsan.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sanger.gymsan.dto.auth.ChangeUserPassword;
import com.sanger.gymsan.dto.auth.CreateUserClientDto;
import com.sanger.gymsan.dto.user.CheckEmailIsValidDto;
import com.sanger.gymsan.dto.user.CreateUserDto;
import com.sanger.gymsan.dto.user.UpdateAcountDto;
import com.sanger.gymsan.dto.user.UpdateUserDto;
import com.sanger.gymsan.dto.user.UserDtoConverter;
import com.sanger.gymsan.exceptions.PasswordNotMismatch;
import com.sanger.gymsan.exceptions.UserNotFoundException;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.UserEntityRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService extends BaseService<Usuario, Long, UserEntityRepository> {

    private final UserDtoConverter userDtoConverter;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenService verificationTokenService;

    private final RolService rolService;

    @Autowired
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Usuario> findUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public List<Usuario> findAllByRol(boolean includeDeleted, List<String> roles) {

        Session session = entityManager.unwrap(Session.class);

        if (!includeDeleted) {
            session.enableFilter("deletedFilter").setParameter("isDeleted", includeDeleted);
        } else {
            session.disableFilter("deletedFilter");
        }
        session.disableFilter("deletedFilter");
        return repository.findUsuariosByRoles(roles);
    }

    public Page<Usuario> filterUser(String filter, Pageable pageable) {
        return this.repository.findByUsernameIgnoreCaseContainingOrFullNameIgnoreCaseContaining(filter, filter,
                pageable);
    }

    public Usuario newUser(CreateUserDto newUser) {

        Usuario userEntity = userDtoConverter.convertCreateUserDtoToUserEntity(newUser);
        Usuario userSaved = save(userEntity);
        verificationTokenService.sendEmailVerification(userSaved);

        return userSaved;

    }

    public Usuario newUserClient(CreateUserClientDto newUser) {

        Usuario userEntity = userDtoConverter.convertCreateUserClientDtoToUserEntity(newUser);
        Usuario userSaved = save(userEntity);

        verificationTokenService.sendEmailVerification(userSaved);

        return userSaved;

    }

    public Usuario updateUser(UpdateUserDto updateUserDto) {

        try {
            Usuario userEntity = findById(updateUserDto.getId())
                    .orElseThrow(() -> new UserNotFoundException(updateUserDto.getId()));

            userEntity.setEmail(updateUserDto.getEmail());
            userEntity.setRoles(rolService.getAllRolesFromStrings(updateUserDto.getRoles()));
            return update(userEntity);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe");
        }

    }

    public Usuario updateAcount(UpdateAcountDto updateAcountDto, Usuario user) {

        try {
            Usuario userEntity = findById(user.getId()).orElseThrow(() -> new UserNotFoundException());

            modelMapper.map(updateAcountDto, userEntity);
            return update(userEntity);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    public Usuario updatePassword(ChangeUserPassword user, Usuario currentUser) throws UserNotFoundException {
        Usuario userEntity = findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(currentUser.getId()));

        if (!passwordEncoder.matches(user.getOldPassword(), userEntity.getPassword())) {
            throw new PasswordNotMismatch(true);
        } else if (!user.getPassword().equals(user.getPassword2())) {
            throw new PasswordNotMismatch();
        } else {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            return update(userEntity);
        }

    }

    public boolean changeUserStatus(Long id) throws UserNotFoundException {
        Usuario userEntity = findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userEntity.setEnabled(!userEntity.isEnabled());
        this.repository.save(userEntity);

        return userEntity.isEnabled();
    }

    public boolean checkEmailIsValid(CheckEmailIsValidDto checkEmailIsValidDto) {
        return this.repository.findByEmailAndIdNot(checkEmailIsValidDto.getEmail(), checkEmailIsValidDto.getId())
                .isPresent();

    }

    public Usuario findByDocumento(String documento) {
        return this.repository.findByDocumento(documento).orElseThrow(() -> new UserNotFoundException());

    }

}
