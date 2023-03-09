package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testGetByCode() {
        // given
        String code = "6545245";
        Client expectedClient = new Client(code, "John Doe","0656545245", "john.doe@example.com");
        when(clientRepository.findByCode(code)).thenReturn(Optional.of(expectedClient));

        // when
        Client actualClient = clientService.getByCode(code);

        // then
        assertEquals(expectedClient, actualClient);
        verify(clientRepository,times(1)).findByCode(code);
    }

    @Test
    public void testGetByName() {
        // given
        String name = "Doe";
        List<Client> expectedClients = Arrays.asList(
                new Client("1", "John Doe","0656545245", "john.doe@example.com"),
                new Client("2", "Jane Doe", "0656545245","jane.doe@example.com"));
        when(clientRepository.findByNameContaining(name)).thenReturn(expectedClients);

        // when
        List<Client> actualClients = clientService.getByName(name);

        // then
        assertEquals(expectedClients, actualClients);
        verify(clientRepository).findByNameContaining(name);
    }

    @Test
    public void testCreate() {
        // given
        Client clientToCreate = new Client("1", "John Doe","0656545245", "john.doe@example.com");
        when(clientRepository.save(clientToCreate)).thenReturn(clientToCreate);

        // when
        Client createdClient = clientService.create(clientToCreate);

        // then
        assertEquals(clientToCreate, createdClient);
        verify(clientRepository).save(clientToCreate);
    }

    @Test
    public void testGetAll() {
        // given
        List<Client> expectedClients = Arrays.asList(
                new Client("1", "John Doe","0656545245", "john.doe@example.com"),
                new Client("2", "Jane Doe","0656545245", "jane.doe@example.com"));
        when(clientRepository.findAll()).thenReturn(expectedClients);

        // when
        List<Client> actualClients = clientService.getAll();

        // then
        assertEquals(expectedClients, actualClients);
        verify(clientRepository).findAll();
    }

    @Test
    public void testDelete() {
        // given
        String codeToDelete = "24515454561";
        when(clientRepository.findByCode(codeToDelete)).thenReturn(Optional.of(new Client()));
        // when
        clientService.delete(codeToDelete);

        // then
        verify(clientRepository,times(1)).deleteByCode(codeToDelete);
    }

    @Test
    public void testUpdate() {
        // given
        Client clientToUpdate = new Client("1", "John Doe","0656545245", "john.doe@example.com");
        when(clientRepository.findByCode(clientToUpdate.getCode())).thenReturn(Optional.of(clientToUpdate));
        when(clientRepository.save(clientToUpdate)).thenReturn(clientToUpdate);

        // when
        Client updatedClient = clientService.update(clientToUpdate);

        // then
        assertEquals(clientToUpdate, updatedClient);
        verify(clientRepository,times(1)).findByCode(clientToUpdate.getCode());
        verify(clientRepository,times(1)).save(clientToUpdate);
    }
}
