
package com.sanger.gymsan.dto.membresiasUsuarios;

import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.models.MembresiaUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaUsuarioPairDTO {
    private Membresia membresia;
    private MembresiaUsuario membresiaUsuario;
}