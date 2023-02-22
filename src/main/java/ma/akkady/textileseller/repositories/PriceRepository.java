package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price,Long> {
}
