package com.sanger.gymsan.dto.rutina;

import jakarta.validation.constraints.NotNull;
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
public class AddRemoveUserRutinaDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long rutinaId;

}
