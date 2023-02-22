package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    void deleteByCode(String code);

    List<Client> findByName(String name);

    Client findByCode(String code);
}

