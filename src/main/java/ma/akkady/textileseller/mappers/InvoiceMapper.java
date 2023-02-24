package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.entities.Invoice;
import ma.akkady.textileseller.entities.InvoiceEntry;
import ma.akkady.textileseller.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {VendorMapper.class})
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    @Mapping(target = "invoiceRef", source = "ref")
    @Mapping(target = "entries", source = "entries" ,defaultExpression = "java(entriesToMap)")
    InvoiceToDisplayDto toDisplayedDto(Invoice invoice);

    List<InvoiceToDisplayDto> toDisplayedDtos(List<Invoice> invoices);

    default Map<Product, List<Double>> entriesToMap (Set<InvoiceEntry> entries) {
        return entries.stream()
                .collect(Collectors.groupingBy(InvoiceEntry::getProduct, Collectors.mapping(InvoiceEntry::getEntry, Collectors.toList())));
    }
}
