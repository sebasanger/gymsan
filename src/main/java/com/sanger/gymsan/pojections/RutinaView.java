package com.sanger.gymsan.pojections;

import java.util.Set;

public interface RutinaView {

    Long getId();

    String getNombre();

    String getDescripcion();

    Set<UsuarioMinView> getUsuarios();
}
