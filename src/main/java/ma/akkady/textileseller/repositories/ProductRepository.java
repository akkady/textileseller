package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContaining(String name);

    Optional<Product> findByRef(String ref);
}
