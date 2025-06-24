package it.epicode.B_S7_E5.repository;

import it.epicode.B_S7_E5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{
    public Optional<Utente> findByUsername(String username);//Cerco l'utente in base all'username
    boolean existsByUsername(String username);//Evito username duplicati in fase di registrazione
}
