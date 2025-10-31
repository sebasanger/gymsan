package com.sanger.gymsan.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.models.Rol;
import com.sanger.gymsan.repository.RolRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RolService {

    private final RolRepository rolRepository;

    public Set<Rol> getAllRolesFromStrings(Set<String> roles) {
        return roles.stream().map(rol -> rolRepository.findByRol(rol)).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toSet());

    }

}
