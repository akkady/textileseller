package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.repositories.ClientRepository;
import ma.akkady.textileseller.services.ClientService;
import ma.akkady.textileseller.utils.ReferenceGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client getByCode(String code) {
        return clientRepository.findByCode(code);
    }

    @Override
    public List<Client> getByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public Client create(Client client) {
        client.setCode(ReferenceGenerator.genStringRef());
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public void delete(String code) {
        clientRepository.deleteByCode(code);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }
}
