package ma.akkady.textileseller.services;


import ma.akkady.textileseller.entities.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProductService {

    public Product getProduct(String  ref);

    public List<Product> getAll();

    public Product create(Product product);

    List<Product> findProductsByName(String name);
}
