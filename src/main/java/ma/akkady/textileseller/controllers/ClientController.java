package ma.akkady.textileseller.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static ma.akkady.textileseller.constants.MappingUrls.API_URL;
import static ma.akkady.textileseller.constants.MappingUrls.CLIENTS;

@RestController
@RequestMapping(CLIENTS.BASE_URL)
@RequiredArgsConstructor
@Api(value = "Clients API", tags = CLIENTS.TAG)
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ApiOperation(value = "Create client", tags = "Client")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Client created", response = Client.class)})
    public ResponseEntity<Client> create(@RequestBody Client client) {
        Client createdClient = clientService.create(client);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}")
                        .buildAndExpand(createdClient.getCode()).toUri())
                .body(createdClient);
    }

    @PutMapping
    @ApiOperation(value = "Update client", tags = "Client")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Client updated", response = Client.class), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Client> update(@RequestBody Client client) {
        Client updatedClient = clientService.update(client);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping
    @ApiOperation(value = CLIENTS.GET_API_DESCRIPTION, tags = CLIENTS.TAG)
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping(CLIENTS.SEARCH_BY_CODE)
    @ApiOperation(value = "Get client by code", tags = "Client")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Client found", response = Client.class), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Client> getByCode(@ApiParam(value = "Client code", required = true) @PathVariable String code) {
        return ResponseEntity.ok(clientService.getByCode(code));
    }

    @GetMapping(CLIENTS.SEARCH)
    @ApiOperation(value = "Get client by name", tags = "Client")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Client found", response = Client.class), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<List<Client>> getByName(@ApiParam(value = "Client name", required = true) @RequestParam String name) {
        return ResponseEntity.ok(clientService.getByName(name));
    }

    @DeleteMapping(CLIENTS.BY_CODE)
    @ApiOperation(value = "Delete client", tags = {"Client"})
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Client deleted"), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Void> delete(@ApiParam(value = "Client code", required = true) @PathVariable String code) {
        clientService.delete(code);
        return ResponseEntity.noContent().build();
    }
}

