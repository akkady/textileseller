package ma.akkady.textileseller.services;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequestDto;

import java.util.List;

public interface VendorService {
     VendorInfoDto create(VendorInfoDto vendor);
     VendorInfoDto getVendor(String userName);

    List<VendorInfoDto> getVendors();
     void createOrUpdatePwd(VendorSubscriptionRequestDto vendorSubreption) ;
}
