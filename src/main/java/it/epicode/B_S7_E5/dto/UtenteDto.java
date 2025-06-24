package it.epicode.B_S7_E5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UtenteDto {
    @NotEmpty(message = "Obbligatorio inserire nome")
    private String nome;
    @NotEmpty(message = "Obbligatorio inserire cognome")
    private String cognome;
    @Email(message = "Obbligatorio inserire email (indirizzo@epicode.it)")
    private String email;
    @NotEmpty(message = "Obbligatorio inserire username")
    private String username;
    @NotEmpty(message = "Obbligatorio inserire password")
    private String password;
}
