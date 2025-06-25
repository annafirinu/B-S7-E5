package it.epicode.B_S7_E5.controller;

import it.epicode.B_S7_E5.dto.EventoDto;

import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Evento;

import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.service.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/eventi")
public class EventoController {

    //Inietto il service
    @Autowired
    private EventoService eventoService;

    //Metodo per salvare un evento
    @PostMapping("")
    @PreAuthorize(("hasAuthority('ORGANIZZATORE')"))
    @ResponseStatus(HttpStatus.CREATED)
    public Evento saveEvento (@RequestBody @Validated EventoDto eventoDto, Utente utenteAutenticato, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return eventoService.saveEvento(eventoDto,utenteAutenticato);
    }

    //Metodo per estrarre tutti gli eventi
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('UTENTE','ORGANIZZATORE'")
    public Page<Evento> getAllEventi(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy){
        return eventoService.getAllEventi(page, size, sortBy);
    }

    //Metodo per trovare un evento
    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable int id) throws NotFoundException {
        return eventoService.getEvento(id);
    }

    //Metodo per aggiornare un evento
    @PutMapping("/{id}")
    public Evento updateEvento(@PathVariable int id,@RequestBody @Validated EventoDto eventoDto, Utente utenteAutenticato,BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));}
        return eventoService.modificaEvento(id, eventoDto,utenteAutenticato);
    }

    //Metodo per cancellare un evento
    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable int id, Utente utenteAutenticato) throws NotFoundException, ValidationException {
        eventoService.eliminaEvento(id, utenteAutenticato);
    }

}



