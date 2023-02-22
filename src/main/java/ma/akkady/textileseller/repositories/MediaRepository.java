package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Product,Long> {
}
