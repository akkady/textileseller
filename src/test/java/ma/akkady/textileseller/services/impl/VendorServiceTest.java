package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequestDto;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.mappers.VendorMapper;
import ma.akkady.textileseller.repositories.VendorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private VendorMapper vendorMapper;

    @InjectMocks
    private VendorServiceImpl vendorService;

    @Test
    public void testCreate() {
        VendorInfoDto vendorInfoDto = new VendorInfoDto();
        vendorInfoDto.setFirstname("Test Vendor");
        vendorInfoDto.setUsername("test@example.com");
        vendorInfoDto.setPhoneNumber("1234567890");
        vendorInfoDto.setAddress("Test Address");

        Vendor vendor = new Vendor();
        vendor.setFirstname("Test Vendor");
        vendor.setUsername("test@example.com");
        vendor.setPhoneNumber("1234567890");
        vendor.setAddress("Test Address");

        when(vendorMapper.toEntity(any(VendorInfoDto.class))).thenReturn(vendor);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.toDto(any(Vendor.class))).thenReturn(vendorInfoDto);

        VendorInfoDto createdVendor = vendorService.create(vendorInfoDto);

        Assertions.assertEquals(vendor.getFirstname(), createdVendor.getFirstname());
        Assertions.assertEquals(vendor.getUsername(), createdVendor.getUsername());
        Assertions.assertEquals(vendor.getPhoneNumber(), createdVendor.getPhoneNumber());
        Assertions.assertEquals(vendor.getAddress(), createdVendor.getAddress());
    }

    @Test
    public void testCreateWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendorService.create(null));
    }


    @Test
    public void testCreateOrUpdatePwd() throws IllegalAccessException {
        Vendor vendor = new Vendor();
        vendor.setUsername("test");
        vendor.setPassword("oldPassword");

        VendorSubscriptionRequestDto dto = new VendorSubscriptionRequestDto();
        dto.setUsername("test");
        dto.setOldPwd("oldPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("newPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.of(vendor));
        vendorService.createOrUpdatePwd(dto);

        Assertions.assertEquals("newPassword", vendor.getPassword());
    }

    @Test
    public void testCreateOrUpdatePwdWithInvalidOldPwd() {
        Vendor vendor = new Vendor();
        vendor.setUsername("test");
        vendor.setPassword("oldPassword");

        VendorSubscriptionRequestDto dto = new VendorSubscriptionRequestDto();
        dto.setUsername("test");
        dto.setOldPwd("wrongPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("newPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.of(vendor));
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendorService.createOrUpdatePwd(dto));
    }

    @Test
    public void testCreateOrUpdatePwdWithNonMatchingPasswords() {
        Vendor vendor = new Vendor();
        vendor.setUsername("test");
        vendor.setPassword("oldPassword");

        VendorSubscriptionRequestDto dto = new VendorSubscriptionRequestDto();
        dto.setUsername("test");
        dto.setOldPwd("oldPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("wrongPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.of(vendor));
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendorService.createOrUpdatePwd(dto));
    }

    @Test
    public void testCreateOrUpdatePwdWithVendorNotFound() {
        VendorSubscriptionRequestDto dto = new VendorSubscriptionRequestDto();
        dto.setUsername("test");
        dto.setOldPwd("oldPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("newPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> vendorService.createOrUpdatePwd(dto));
    }
}
