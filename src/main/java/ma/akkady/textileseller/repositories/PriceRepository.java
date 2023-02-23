package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price,Long> {
    Optional<List<Price>> findByProductRef(String productRef);
}
