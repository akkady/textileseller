package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequest;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.exceptions.PasswordConfirmationException;
import ma.akkady.textileseller.exceptions.UserNotFoundException;
import ma.akkady.textileseller.mappers.VendorMapper;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.VendorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author akkad younes
 */
@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public VendorInfoDto create(VendorInfoDto vendor) {
        return vendorMapper.toDto(vendorRepository.save(vendorMapper.toEntity(vendor)));
    }


    @Override
    public VendorInfoDto getVendor(String userName) {
        Optional<Vendor> storedEntity = vendorRepository.findByUsername(userName);
        return storedEntity
                .map(vendorMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public void createOrUpdatePwd(VendorSubscriptionRequest vendorInReq) {
        Vendor vendor = vendorRepository.findByUsername(vendorInReq.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));

        String oldPassword = vendor.getPassword();
        String newPassword = vendorInReq.getNewPwd();
        String pwdConfirmation = vendorInReq.getPwdConfirmation();
        String enteredOldPassword = vendorInReq.getOldPwd();

        if (!isSamePwd(newPassword, pwdConfirmation)) {
            throw new PasswordConfirmationException();
        }

        if (StringUtils.isEmpty(oldPassword)) {
            vendor.setPassword(newPassword);
        } else if (isSamePwd(enteredOldPassword, oldPassword)) {
            vendor.setPassword(newPassword);
        } else {
            throw new PasswordConfirmationException("Invalid old password");
        }
    }


    private boolean isSamePwd(String pwd, String pwdConfirmation) {
        return StringUtils.equals(pwd, pwdConfirmation);
    }
}