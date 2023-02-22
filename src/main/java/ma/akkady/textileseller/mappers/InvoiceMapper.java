package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.InvoiceDto;
import ma.akkady.textileseller.entities.Invoice;
import ma.akkady.textileseller.utils.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(uses = InvoiceEntryMapper.class)
public interface InvoiceMapper extends EntityMapper<Invoice, InvoiceDto> {
}
