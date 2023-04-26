package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.dtos.ProductDto;
import ma.akkady.textileseller.entities.Invoice;
import ma.akkady.textileseller.entities.InvoiceEntry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {VendorMapper.class,ProductMapper.class,ClientMapper.class})
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    @Mapping(target = "invoiceRef", source = "ref")
    @Mapping(target = "entries", source = "entries" ,defaultExpression = "java(entriesToMap)")
    InvoiceToDisplayDto toDisplayedDto(Invoice invoice);
    @InheritInverseConfiguration
    @Mapping(target = "entries", ignore = true)
    Invoice toEntity(InvoiceToDisplayDto toDisplayDto);

    List<InvoiceToDisplayDto> toDisplayedDtos(List<Invoice> invoices);

    default Map<ProductDto, List<Double>> entriesToMap (Set<InvoiceEntry> entries) {
        if (entries == null) {
            return Collections.emptyMap();
        }
        return entries.stream()
                .collect(Collectors.groupingBy(ie ->MAPPER.toDto(ie.getProduct()), Collectors.mapping(InvoiceEntry::getEntry, Collectors.toList())));
    }
}
