package it.epicode.B_S7_E5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.B_S7_E5.exception.NotFoundException;
import it.epicode.B_S7_E5.model.Utente;
import it.epicode.B_S7_E5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UtenteService utenteService;

    public String createToken(Utente utente){

        return Jwts.builder().issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+durata)).
                subject(String.valueOf(utente.getId())).
                signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                compact();
    }


    //metodo per la verifica della validità del token
    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parse(token);
    }


    //metodo per estrarre l'utente collegato al token
    public Utente getUserFromToken(String token) throws NotFoundException {
        //recuperare l'id dell'utente dal token
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());

        return utenteService.getUtente(id);
    }

}

