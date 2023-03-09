package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequestDto;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.exceptions.PasswordConfirmationException;
import ma.akkady.textileseller.exceptions.UserNotFoundException;
import ma.akkady.textileseller.mappers.VendorMapper;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.VendorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author akkad younes
 */
@Service @Transactional
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public VendorInfoDto create(VendorInfoDto vendorInfo) {
        log.info("Creating Vendor {}", vendorInfo);
        return vendorMapper.toDto(vendorRepository.save(vendorMapper.toEntity(vendorInfo)));
    }


    @Override
    public VendorInfoDto getByUsername(String userName) {
        log.info("Retrieve vendor by username {}", userName);
        Optional<Vendor> storedEntity = vendorRepository.findByUsername(userName);
        return storedEntity
                .map(vendorMapper::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public VendorInfoDto getById(Long id) {
        log.info("Retrieve vendor by id {}", id);
        return vendorRepository.findById(id)
                .map(vendorMapper::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<VendorInfoDto> getVendors() {
        return vendorRepository.findAll()
                .stream().map(vendorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VendorInfoDto update(VendorInfoDto vendorInfo) {
        vendorRepository.findById(vendorInfo.getId()).orElseThrow(UserNotFoundException::new);
        Vendor storedVendor = vendorMapper.toEntity(vendorInfo);
        return vendorMapper.toDto(vendorRepository.save(storedVendor));
    }

    @Override
    public void createOrUpdatePwd(VendorSubscriptionRequestDto vendorInReq) {
        log.info("Creating or updating password for vendor with username {}", vendorInReq.getUsername());
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
