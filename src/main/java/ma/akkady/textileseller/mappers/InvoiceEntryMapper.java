package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.entities.InvoiceEntry;
import ma.akkady.textileseller.utils.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InvoiceEntryMapper extends EntityMapper<InvoiceEntry, InvoiceEntryDto> {
    @Mapping(source = "invoice.id",target = "invoiceId")
    @Mapping(source = "product.id",target = "productId")
    public InvoiceEntryDto toDto(InvoiceEntry invoiceEntry);
}
