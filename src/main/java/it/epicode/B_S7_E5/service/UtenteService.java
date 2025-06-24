package it.epicode.B_S7_E5.service;

import it.epicode.B_S7_E5.dto.UtenteDto;
import it.epicode.B_S7_E5.enumeration.RuoloUtente;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Metodo che aggiunge un utente
    public Utente save(UtenteDto utentedto) throws ValidationException {
        if (utenteRepository.existsByUsername(utentedto.getUsername())) {
            throw new ValidationException("Username già in uso");
        }

        Utente utente = new Utente();
        utente.setNome(utentedto.getNome());
        utente.setCognome(utentedto.getCognome());
        utente.setUsername(utentedto.getUsername());
        utente.setEmail(utentedto.getEmail());
        utente.setPassword(passwordEncoder.encode(utentedto.getPassword()));
        utente.setRuoloUtente(RuoloUtente.UTENTE);
        return utenteRepository.save(utente);
    }

    //Metodo per estrarre tutti gli utenti
    public Page<Utente> getAllUtenti(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    //Metodo che restituisce un solo utente
    public Utente getUtente(int id) throws NotFoundException{
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente con id " + id + " non presente"));
    }

    //Metodo per aggiornare un utente
    public Utente updateUtente(int id, UtenteDto utenteDto, Utente utenteAutenticato) throws NotFoundException, ValidationException {
        if (utenteAutenticato.getId() != id) {
            throw new ValidationException("Puoi modificare solo il tuo profilo");
        }

        Utente utente = getUtente(id);

        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));

        return utenteRepository.save(utente);
    }


    //Metodo per cancellare un utente
    public void deleteUtente(int id, Utente utenteAutenticato) throws NotFoundException, ValidationException {
        if (utenteAutenticato.getId() != id) {
            throw new ValidationException("Puoi eliminare solo il tuo profilo");
        }

        Utente utente = getUtente(id);
        utenteRepository.delete(utente);
    }
}
