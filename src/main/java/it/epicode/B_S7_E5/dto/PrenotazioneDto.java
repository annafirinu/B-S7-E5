package it.epicode.B_S7_E5.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDto {
    @NotNull(message = "Obbligatorio inserire data")
    private LocalDate dataPrenotazione;
    private int eventoId;
    private int utenteId;
}