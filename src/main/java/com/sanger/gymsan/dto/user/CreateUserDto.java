package com.sanger.gymsan.dto.user;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.sanger.gymsan.models.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String avatar;

    @NotBlank
    @Length(min = 5, max = 50)
    private String fullName;

    @NotBlank
    @Email()
    private String email;

    @NotEmpty()
    private Set<String> roles;

    @NotEmpty()
    private String documento;

}
