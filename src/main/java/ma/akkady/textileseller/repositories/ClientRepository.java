package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    void deleteByCode(String code);

    List<Client> findByFirstnameContaining(String name);

    Optional<Client> findByCode(String code);
}

