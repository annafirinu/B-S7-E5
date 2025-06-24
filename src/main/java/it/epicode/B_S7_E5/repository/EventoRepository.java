package it.epicode.B_S7_E5.repository;

import it.epicode.B_S7_E5.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer>,
        PagingAndSortingRepository<Evento, Integer> {
}
