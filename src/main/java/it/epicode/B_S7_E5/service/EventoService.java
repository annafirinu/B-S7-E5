package it.epicode.B_S7_E5.service;

import it.epicode.B_S7_E5.dto.EventoDto;
import it.epicode.B_S7_E5.enumeration.RuoloUtente;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.UnAuthorizedException;
import it.epicode.B_S7_E5.model.Evento;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.repository.EventoRepository;
import it.epicode.B_S7_E5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private  EventoRepository eventoRepository;
    @Autowired
    private  UtenteRepository utenteRepository;

    //Metodo per creare un evento
    public Evento saveEvento(EventoDto eventodto, Utente organizzatore) {
        if (organizzatore.getRuoloUtente() != it.epicode.B_S7_E5.enumeration.RuoloUtente.ORGANIZZATORE) {
            throw new UnAuthorizedException("Solo gli organizzatori possono creare eventi");
        }

        Evento evento = new Evento();
        evento.setTitolo(eventodto.getTitolo());
        evento.setDescrizione(eventodto.getDescrizione());
        evento.setData(eventodto.getData());
        evento.setLuogo(eventodto.getLuogo());
        evento.setNumeroPosti(eventodto.getNumeroPosti());
        evento.setOrganizzatore(organizzatore);

        return eventoRepository.save(evento);
    }

    //Metodo per estrarre tutti gli eventi
    public Page<Evento> getAllEventi(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventoRepository.findAll(pageable);
    }

    //Metodo che restituisce un solo evento
    public Evento getEvento(int id) throws NotFoundException{
        return eventoRepository.findById(id).orElseThrow(()->new NotFoundException("Evento con id " + id + " non presente"));
    }

    //Metodo per modificare un evento
    public Evento modificaEvento(int eventoId, EventoDto eventoDto, Utente organizzatore) throws NotFoundException {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        if (evento.getOrganizzatore().getId() != organizzatore.getId()) {
            throw new UnAuthorizedException("Non puoi modificare un evento che non hai creato");
        }

        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setData(eventoDto.getData());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setNumeroPosti(eventoDto.getNumeroPosti());

        return eventoRepository.save(evento);
    }

    //Metodo per eliminare un evento
    public void eliminaEvento(int eventoId, Utente organizzatore) throws NotFoundException {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        if (evento.getOrganizzatore().getId() != organizzatore.getId()) {
            throw new UnAuthorizedException("Non puoi eliminare un evento che non hai creato");
        }

        eventoRepository.delete(evento);
    }

}