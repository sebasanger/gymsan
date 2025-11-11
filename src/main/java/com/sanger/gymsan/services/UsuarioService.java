package com.sanger.gymsan.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sanger.gymsan.dto.auth.ChangeUserPassword;
import com.sanger.gymsan.dto.user.CheckEmailIsValidDto;
import com.sanger.gymsan.dto.user.CreateUserDto;
import com.sanger.gymsan.dto.user.UpdateAcountDto;
import com.sanger.gymsan.dto.user.UpdateUserDto;
import com.sanger.gymsan.dto.user.UserDtoConverter;
import com.sanger.gymsan.exceptions.PasswordNotMismatch;
import com.sanger.gymsan.exceptions.UserNotFoundException;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService extends BaseService<Usuario, Long, UserEntityRepository> {

    private final UserDtoConverter userDtoConverter;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenService verificationTokenService;

    private final RolService rolService;

    @Value("${base.url.frontend}")
    private String urlFrontend;

    @Value("${redirect.path.active.user}")
    private String activateUserPath;

    public Optional<Usuario> findUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public Page<Usuario> filterUser(String filter, Pageable pageable) {
        return this.repository.findByUsernameIgnoreCaseContainingOrFullNameIgnoreCaseContaining(filter, filter,
                pageable);
    }

    public Usuario newUser(CreateUserDto newUser) {

        Usuario userEntity = userDtoConverter.convertCreateUserDtoToUserEntity(newUser);
        Usuario userSaved = save(userEntity);

        verificationTokenService.sendEmailVerification(userSaved, this.urlFrontend + "/" + this.activateUserPath);

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

    public Usuario updateAcount(UpdateAcountDto updateAcountDto) {

        try {
            Usuario userEntity = findById(updateAcountDto.getId()).orElseThrow(() -> new UserNotFoundException());

            userEntity.setEmail(updateAcountDto.getEmail());
            return update(userEntity);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    public Usuario updatePassword(ChangeUserPassword user) throws UserNotFoundException {
        Usuario userEntity = findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));

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
