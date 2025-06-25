package it.epicode.B_S7_E5.controller;

import it.epicode.B_S7_E5.dto.PrenotazioneDto;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Prenotazione;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/prenotazioni")
public class PrenotazioneController {

    //Inietto il service
    @Autowired
    private PrenotazioneService prenotazioneService;

    //Metodo per salvare una prenotazione
    @PostMapping("")
    @PreAuthorize(("hasAuthority('UTENTE')"))
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione saveUtente (@RequestBody @Validated PrenotazioneDto prenotazioneDto, Utente utenteAutenticato, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto,utenteAutenticato);
    }

    //Metodo per estrarre tutte le prenotazioni
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('UTENTE','ORGANIZZATORE'")
    public Page<Prenotazione> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy){
        return prenotazioneService.getAllPrenotazioni(page, size, sortBy);
    }

    //Metodo per trovare una prenotazione
    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    //Metodo per aggiornare una prenotazione
    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable int id,@RequestBody @Validated PrenotazioneDto prenotazioneDto, Utente utenteAutenticato,BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));}
        return prenotazioneService.updatePrenotazione(id, prenotazioneDto,utenteAutenticato);
    }

    //Metodo per cancellare una prenotazione
    @DeleteMapping("/{id}")
    public void deletePrenotazione(@PathVariable int id, Utente utenteAutenticato) throws NotFoundException, ValidationException {
        prenotazioneService.deletePrenotazione(id, utenteAutenticato);
    }

}


