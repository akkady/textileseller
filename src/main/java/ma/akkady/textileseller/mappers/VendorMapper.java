package ma.akkady.textileseller.mappers;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.utils.EntityMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VendorMapper extends EntityMapper<Vendor, VendorInfoDto> {
}
