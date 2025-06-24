package it.epicode.B_S7_E5.repository;

import it.epicode.B_S7_E5.model.Prenotazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

    Page<Prenotazione> findByUtente(int utenteId, Pageable pageable);//Ottengo tutte le prenotazioni di un utente
    long countByEvento (int eventoId);//Ottengo quante prenotazioni ci sono per un evento
}
