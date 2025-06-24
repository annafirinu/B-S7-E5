package it.epicode.B_S7_E5.service;

import it.epicode.B_S7_E5.dto.LoginDto;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.repository.UtenteRepository;
import it.epicode.B_S7_E5.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String login(LoginDto loginDto) throws NotFoundException {
        Utente utente = utenteRepository.findByUsername(loginDto.getUsername()).
                orElseThrow(() -> new NotFoundException("Utente con questo username/password non trovato"));

        if(passwordEncoder.matches(loginDto.getPassword(),utente.getPassword())){
            return jwtTool.createToken(utente);
        }
        else{
            throw new NotFoundException("Utente con questo username/password non trovato");
        }
    }
}
