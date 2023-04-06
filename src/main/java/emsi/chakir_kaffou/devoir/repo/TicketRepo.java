package emsi.chakir_kaffou.devoir.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import emsi.chakir_kaffou.devoir.models.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    Optional<List<Ticket>> findByClientId(int id);

    Optional<List<Ticket>> findByDevId(int id);
}
