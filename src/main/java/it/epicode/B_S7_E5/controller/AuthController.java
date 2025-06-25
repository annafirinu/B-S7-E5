package it.epicode.B_S7_E5.controller;

import it.epicode.B_S7_E5.dto.LoginDto;
import it.epicode.B_S7_E5.dto.UtenteDto;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.exception.ValidationException;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.service.AuthService;
import it.epicode.B_S7_E5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;


    @PostMapping("/auth/register")
    public Utente register(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }
        return utenteService.save(utenteDto);
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }


        return authService.login(loginDto);
    }
}
