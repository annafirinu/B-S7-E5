package it.epicode.B_S7_E5.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    //Dati che vogliamo mostrare al client quando c'è un errore
    private String message;
    private LocalDateTime dataErrore;
}