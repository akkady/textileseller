package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Optional<Vendor> findByUsername(String username);
}

