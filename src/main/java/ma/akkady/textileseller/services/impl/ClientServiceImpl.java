package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.exceptions.UserNotFoundException;
import ma.akkady.textileseller.repositories.ClientRepository;
import ma.akkady.textileseller.services.ClientService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author younes akkad
 */
@Service @Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    @Override
    public Client getByCode(String code) {
        log.info("Retrieving client with code {}",code);
        return clientRepository.findByCode(code).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<Client> getByName(String name) {
        log.info("Retrieving clients with name contains {}",name);
        return clientRepository.findByFirstnameContaining(name);
    }

    @Override
    public Client create(Client client) {
        log.info("Creating a new client : {}",client);
        client.setCode(RandomStringUtils.randomNumeric(12));
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAll() {
        log.info("Retrieving all clients");
        return clientRepository.findAll();
    }

    @Override
    public void delete(String code) {
        log.info("Deleting client with code {}",code);
        getByCode(code);
        clientRepository.deleteByCode(code);
    }

    @Override
    public Client update(Client client) {
        log.info("Updating clint info for client with code {}",client.getCode());
        getByCode(client.getCode());
        return clientRepository.save(client);
    }
}
