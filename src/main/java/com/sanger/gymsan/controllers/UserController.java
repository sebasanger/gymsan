package com.sanger.gymsan.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.auth.ChangeUserPassword;
import com.sanger.gymsan.dto.auth.CreateUserClientDto;
import com.sanger.gymsan.dto.user.CheckEmailIsValidDto;
import com.sanger.gymsan.dto.user.CreateUserDto;
import com.sanger.gymsan.dto.user.GetUserDetailsDto;
import com.sanger.gymsan.dto.user.GetUsersDto;
import com.sanger.gymsan.dto.user.UpdateAcountDto;
import com.sanger.gymsan.dto.user.UpdateUserDto;
import com.sanger.gymsan.dto.user.UserDtoConverter;
import com.sanger.gymsan.exceptions.EntitiesNotFoundException;
import com.sanger.gymsan.exceptions.UserNotFoundException;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UsuarioService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam(value = "rol", required = false) List<String> rol) {
        List<Usuario> result;
        if (rol != null) {
            result = userEntityService.findAllByRol(includeDeleted, rol);
        } else {
            result = userEntityService.findAll(includeDeleted);
        }

        if (result.isEmpty()) {
            throw new EntitiesNotFoundException();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/pageable")
    public ResponseEntity<?> listUsers(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String filter) {
        Page<Usuario> result = userEntityService.filterUser(filter, pageable);

        if (result.isEmpty()) {
            throw new UserNotFoundException();
        } else {
            Page<GetUsersDto> dtoList = result.map(userDtoConverter::convertUserEntityToGetUserDto);

            return ResponseEntity.ok().body(dtoList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDetailsDto> findUserById(@PathVariable Long id) {
        Usuario result = userEntityService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        GetUserDetailsDto user = userDtoConverter.convertUserEntityToGetUserDetailsDto(result);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("save")
    public ResponseEntity<GetUsersDto> newUser(@Valid @RequestBody CreateUserDto newUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.newUser(newUser)));
    }

    @PostMapping("/createClient")
    public ResponseEntity<GetUsersDto> newUser(@Valid @RequestBody CreateUserClientDto newUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.newUserClient(newUser)));
    }

    @PutMapping("update")
    public ResponseEntity<GetUsersDto> updateUser(@Valid @RequestBody UpdateUserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.updateUser(user)));

    }

    @PutMapping("/update-acount")
    public ResponseEntity<GetUsersDto> updateAcount(@Valid @RequestBody UpdateAcountDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.updateAcount(user)));

    }

    @PutMapping("/changePassword")
    public ResponseEntity<GetUsersDto> updateUser(@Valid @RequestBody ChangeUserPassword user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.updatePassword(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        Usuario user = userEntityService.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userEntityService.delete(user);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/checkEmailIsValid")
    public boolean checkEmailIsValid(@Valid @RequestBody CheckEmailIsValidDto checkEmailIsValidDto) {
        return userEntityService.checkEmailIsValid(checkEmailIsValidDto);
    }

    @PutMapping({ "", "/recover/{id}" })
    public ResponseEntity<Usuario> recover(@PathVariable(required = true) Long id) {
        userEntityService.recover(id);
        return ResponseEntity.noContent().build();
    }

}
