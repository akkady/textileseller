package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}

