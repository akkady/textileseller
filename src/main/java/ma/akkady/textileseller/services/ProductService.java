package ma.akkady.textileseller.services;


import ma.akkady.textileseller.entities.Product;

import java.util.List;

public interface ProductService {

    Product getByIdOrThrow(String ref);

    Product getByIdOrThrow(Long id);

    List<Product> getAll();

    Product create(Product product);

    List<Product> findProductsByName(String name);

    Product update(Product product);
}
