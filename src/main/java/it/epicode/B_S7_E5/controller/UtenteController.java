package it.epicode.B_S7_E5.controller;

import it.epicode.B_S7_E5.dto.UtenteDto;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/utenti")
public class UtenteController {

    //Inietto il service
    @Autowired
    private UtenteService utenteService;

    //Metodo per salvare un utente
    @PostMapping("")
    @PreAuthorize(("hasAuthority('UTENTE')"))
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveUtente (@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return utenteService.save(utenteDto);
    }

    //Metodo per estrarre tutti gli UTENTI
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('UTENTE','ORGANIZZATORE'")
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy){
        return utenteService.getAllUtenti(page, size, sortBy);
    }

    //Metodo per trovare un utente
    @GetMapping("/{id}")
    public Utente getUtente(@PathVariable int id) throws NotFoundException {
        return utenteService.getUtente(id);
    }

    //Metodo per aggiornare un utente
    @PutMapping("/{id}")
    public Utente updateUtente(@PathVariable int id,@RequestBody @Validated UtenteDto utenteDto, Utente utenteAutenticato,BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));}
        return utenteService.updateUtente(id, utenteDto,utenteAutenticato);
    }

    //Metodo per cancellare un utente
    @DeleteMapping("/{id}")
    public void deleteStudente(@PathVariable int id, Utente utenteAutenticato) throws NotFoundException, ValidationException {
        utenteService.deleteUtente(id, utenteAutenticato);
    }

}
