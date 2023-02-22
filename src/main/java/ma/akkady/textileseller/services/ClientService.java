package ma.akkady.textileseller.services;

import ma.akkady.textileseller.entities.Client;

import java.util.List;

public interface ClientService {

    Client getByCode(String code);

    List<Client> getByName(String name);

    Client create(Client client);

    List<Client> getAll();

    void delete(String code);

    Client update(Client client);
}
