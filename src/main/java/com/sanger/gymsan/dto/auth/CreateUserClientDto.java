package com.sanger.gymsan.dto.auth;

import org.hibernate.validator.constraints.Length;

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
public class CreateUserClientDto {

    @NotBlank
    @Length(min = 5, max = 50)
    private String fullName;

    @NotBlank
    @Email()
    private String email;

    @NotEmpty()
    private String documento;

}
