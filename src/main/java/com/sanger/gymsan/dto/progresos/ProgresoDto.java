package com.sanger.gymsan.dto.progresos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgresoDto {
    private Long id;
    private Long rutinaId;
    private String nombreRutina;
    private EntrenamientoProgresoDto progresoEntrenamiento;
    private LocalDate fecha;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}