package com.sanger.gymsan.dto.auth;

import jakarta.validation.constraints.NotBlank;
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
public class ChangeUserPassword {

    private Long id;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String password;
    @NotBlank
    private String password2;

}
