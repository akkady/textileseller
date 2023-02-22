package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.ClientDto;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.utils.EntityMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper extends EntityMapper<Client, ClientDto> {
}
