package ma.akkady.textileseller.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@ApiOperation(value = "Client API", tags = "Clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{code}")
    @ApiOperation(value = "Get client by code", tags = {"Client"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Client found", response = Client.class), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Client> getByCode(@ApiParam(value = "Client code", required = true) @PathVariable String code) {
        Client client = clientService.getByCode(code);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get all clients", tags = {"Client"})
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @PostMapping
    @ApiOperation(value = "Create client", tags = {"Client"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Client created", response = Client.class)})
    public ResponseEntity<Client> create(@RequestBody Client client) {
        Client createdClient = clientService.create(client);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(createdClient.getCode()).toUri()).body(createdClient);
    }

    @PutMapping("/{code}")
    @ApiOperation(value = "Update client", tags = {"Client"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Client updated", response = Client.class), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Client> update(@ApiParam(value = "Client code", required = true) @PathVariable String code, @RequestBody Client client) {
        Client existingClient = clientService.getByCode(code);
        if (existingClient != null) {
            client.setCode(code);
            Client updatedClient = clientService.update(client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{code}")
    @ApiOperation(value = "Delete client", tags = {"Client"})
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Client deleted"), @ApiResponse(code = 404, message = "Client not found")})
    public ResponseEntity<Void> delete(@ApiParam(value = "Client code", required = true) @PathVariable String code) {
        Client existingClient = clientService.getByCode(code);
        if (existingClient != null) {
            clientService.delete(code);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

