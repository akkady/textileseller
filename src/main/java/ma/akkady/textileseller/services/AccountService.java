package ma.akkady.textileseller.services;

import ma.akkady.textileseller.entities.SecurityRole;
import ma.akkady.textileseller.entities.Vendor;

import java.util.Collection;

public interface AccountService {

    SecurityRole creteRole(SecurityRole role);

    Vendor addRoleToUser(String username, String roleName);

    Vendor loadUserByUsername(String username);

    Collection<SecurityRole> rolesList();
}