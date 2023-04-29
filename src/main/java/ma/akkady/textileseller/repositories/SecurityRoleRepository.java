package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {
    SecurityRole findByRoleName(String roleName);
}
