package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequest;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.mappers.VendorMapper;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.VendorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        vendorInfoDto.setName("Test Vendor");
        vendorInfoDto.setUserName("test@example.com");
        vendorInfoDto.setPhone("1234567890");
        vendorInfoDto.setAddress("Test Address");

        Vendor vendor = new Vendor();
        vendor.setName("Test Vendor");
        vendor.setUsername("test@example.com");
        vendor.setPhone("1234567890");
        vendor.setAddress("Test Address");

        when(vendorMapper.toEntity(any(VendorInfoDto.class))).thenReturn(vendor);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.toDto(any(Vendor.class))).thenReturn(vendorInfoDto);

        VendorInfoDto createdVendor = vendorService.create(vendorInfoDto);

        Assertions.assertEquals(vendor.getName(), createdVendor.getName());
        Assertions.assertEquals(vendor.getUsername(), createdVendor.getUserName());
        Assertions.assertEquals(vendor.getPhone(), createdVendor.getPhone());
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

        VendorSubscriptionRequest dto = new VendorSubscriptionRequest();
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

        VendorSubscriptionRequest dto = new VendorSubscriptionRequest();
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

        VendorSubscriptionRequest dto = new VendorSubscriptionRequest();
        dto.setUsername("test");
        dto.setOldPwd("oldPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("wrongPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.of(vendor));
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendorService.createOrUpdatePwd(dto));
    }

    @Test
    public void testCreateOrUpdatePwdWithVendorNotFound() {
        VendorSubscriptionRequest dto = new VendorSubscriptionRequest();
        dto.setUsername("test");
        dto.setOldPwd("oldPassword");
        dto.setNewPwd("newPassword");
        dto.setPwdConfirmation("newPassword");

        when(vendorRepository.findByUsername("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> vendorService.createOrUpdatePwd(dto));
    }
}
