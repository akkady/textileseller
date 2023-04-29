package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.SecurityRole;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.exceptions.UserNotFoundException;
import ma.akkady.textileseller.repositories.SecurityRoleRepository;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final VendorRepository vendorRepository;
    private final SecurityRoleRepository securityRoleRepository;


    @Override
    public SecurityRole creteRole(SecurityRole role) {
        return securityRoleRepository.save(role);
    }

    @Override
    public Vendor addRoleToUser(String username, String roleName) {
        Vendor vendor = vendorRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        SecurityRole role = securityRoleRepository.findByRoleName(roleName);
        vendor.getRoles().add(role);
        return vendor;
    }

    @Override
    public Vendor loadUserByUsername(String username) {
        return vendorRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Collection<SecurityRole> rolesList() {
        return securityRoleRepository.findAll();
    }
}
