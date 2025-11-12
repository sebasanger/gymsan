package com.sanger.gymsan.dto.user;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sanger.gymsan.models.Rol;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.RolService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    private RolService rolService;

    public GetUsersDto convertUserEntityToGetUserDto(Usuario user) {
        return modelMapper.map(user, GetUsersDto.class);
    }

    public GetUserDetailsDto convertUserEntityToGetUserDetailsDto(Usuario user) {
        return GetUserDetailsDto.builder().id(user.getId()).fullName(user.getFullName())
                .email(user.getEmail()).enabled(user.isEnabled()).deleted(user.getDeleted()).documento(user.getDocumento())
                .createdAt(user.getCreatedAt()).lastPasswordChangeAt(user.getLastPasswordChangeAt())
                .roles(user.getRoles().stream().map(Rol::getRol).collect(Collectors.toSet())).build();
    }

    public Usuario convertCreateUserDtoToUserEntity(CreateUserDto newUser) {
        return Usuario.builder()
                .password(passwordEncoder.encode("myPasswordEncoded12313123")).enabled(false).deleted(false).documento(newUser.getDocumento())
                .fullName(newUser.getFullName()).email(newUser.getEmail()).roles(rolService.getAllRolesFromStrings(newUser.getRoles())).build();
    }

    public Usuario convertUpdateUserDtoToUserEntity(UpdateUserDto user) {
        return Usuario.builder().fullName(user.getFullName()).username(user.getEmail()).documento(user.getDocumento())
                .email(user.getEmail()).roles(rolService.getAllRolesFromStrings(user.getRoles())).build();
    }

    public Usuario convertUpdateAcountDtoToUserEntity(UpdateAcountDto user) {
        return modelMapper.map(user, Usuario.class);
    }

}
