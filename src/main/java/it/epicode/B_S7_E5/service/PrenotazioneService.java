package it.epicode.B_S7_E5.service;

import it.epicode.B_S7_E5.dto.PrenotazioneDto;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Evento;
import it.epicode.B_S7_E5.model.Prenotazione;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.repository.EventoRepository;
import it.epicode.B_S7_E5.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    //Metodo per fare una prenotazione
    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto, Utente utente) throws NotFoundException, ValidationException {
        Evento evento = eventoRepository.findById(prenotazioneDto.getEventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        long prenotazioniCorrenti = prenotazioneRepository.countByEvento(evento.getId());

        if (prenotazioniCorrenti >= evento.getNumeroPosti()) {
            throw new ValidationException("Posti terminati per questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataPrenotazione(prenotazioneDto.getDataPrenotazione());
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    //Metodo per estrarre tutte le prenotazioni
    public Page<Prenotazione> getAllUtenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    //Metodo che restituisce una sola prenotazione
    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con id " + id + " non presente"));
    }

    //Metodo per modificare una prenotazione
    public Prenotazione updatePrenotazione(int prenotazioneId, PrenotazioneDto prenotazioneDto, Utente utente) throws NotFoundException, ValidationException {
        Prenotazione prenotazionedaAggiornare = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (prenotazionedaAggiornare.getUtente().getId() != utente.getId()) {
            throw new ValidationException("Non puoi modificare una prenotazione che non ti appartiene");
        }

        Evento nuovoEvento = eventoRepository.findById(prenotazioneDto.getEventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        long postiOccupati = prenotazioneRepository.countByEvento(nuovoEvento.getId());
        if (postiOccupati >= nuovoEvento.getNumeroPosti()) {
            throw new ValidationException("Non ci sono posti disponibili per il nuovo evento selezionato");
        }

        prenotazionedaAggiornare.setEvento(nuovoEvento);
        prenotazionedaAggiornare.setDataPrenotazione(prenotazioneDto.getDataPrenotazione());

        return prenotazioneRepository.save(prenotazionedaAggiornare);
    }


    //Metodo per cancellare una prenotazione
    public void deletePrenotazione(int prenotazioneId, Utente utente) throws NotFoundException, ValidationException {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (prenotazione.getUtente().getId() != utente.getId()) {
            throw new ValidationException("Non puoi cancellare una prenotazione che non ti appartiene");
        }

        prenotazioneRepository.delete(prenotazione);
    }
}
