package com.sanger.gymsan.dto.progresoEjercicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sanger.gymsan.models.Rutina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RutinaActivaDto {
    private Rutina rutina;
    private List<EntrenamientoConProgresoDto> entrenamientos;
    private LocalDate fecha;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}