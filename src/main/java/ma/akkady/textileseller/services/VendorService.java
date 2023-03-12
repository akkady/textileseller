package ma.akkady.textileseller.services;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequestDto;

import java.util.List;

public interface VendorService {
    VendorInfoDto create(VendorInfoDto vendorInfo);

    VendorInfoDto getByUsername(String userName);

    VendorInfoDto getByIdOrThrow(Long id);

    List<VendorInfoDto> getVendors();

    VendorInfoDto update(VendorInfoDto vendorInfo);

    void createOrUpdatePwd(VendorSubscriptionRequestDto vendorSubscription);
}
