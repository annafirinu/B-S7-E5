package it.epicode.B_S7_E5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dataPrenotazione;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "evento_id")
    private Evento evento;

}
