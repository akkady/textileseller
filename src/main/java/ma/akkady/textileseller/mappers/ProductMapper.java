package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.ProductDto;
import ma.akkady.textileseller.entities.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productId",source = "id")
    @Mapping(target = "productName",source = "id")
    ProductDto toDto(Product product);

    @InheritInverseConfiguration
    Product toEntity(ProductDto productDto);
}
