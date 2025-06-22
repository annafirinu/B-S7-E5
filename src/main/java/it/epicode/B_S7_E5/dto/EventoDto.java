package it.epicode.B_S7_E5.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {
    @NotEmpty(message = "Obbligatorio inserire titolo")
    private String titolo;
    private String descrizione;
    @NotNull(message = "Obbligatorio inserire data")
    private LocalDate data;
    @NotEmpty(message = "Obbligatorio inserire luogo")
    private String luogo;
    @NotNull(message = "Obbligatorio inserire numero Posti")
    private int numeroPosti;

}
