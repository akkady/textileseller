package ma.akkady.textileseller.services;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequest;

public interface VendorService {
    public VendorInfoDto create(VendorInfoDto vendor);
    public VendorInfoDto getVendor(String userName);
    public void createOrUpdatePwd(VendorSubscriptionRequest vendorSubreption) ;
}
