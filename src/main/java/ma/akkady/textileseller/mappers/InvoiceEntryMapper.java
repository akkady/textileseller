package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.entities.InvoiceEntry;
import ma.akkady.textileseller.utils.EntityMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InvoiceEntryMapper {
    InvoiceEntryMapper INSTANCE = Mappers.getMapper(InvoiceEntryMapper.class);
    @Mapping(source = "invoice.id",target = "invoiceId")
    @Mapping(source = "product.id",target = "productId")
    InvoiceEntryDto toDto(InvoiceEntry invoiceEntry);

    @InheritInverseConfiguration
    InvoiceEntry toEntity(InvoiceEntryDto invoiceEntryDto);

}
