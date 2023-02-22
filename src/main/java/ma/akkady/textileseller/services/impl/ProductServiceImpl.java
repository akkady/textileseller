package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.exceptions.ProductNotFoundException;
import ma.akkady.textileseller.repositories.ProductRepository;
import ma.akkady.textileseller.services.ProductService;
import ma.akkady.textileseller.utils.ReferenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }


    @Override
    public Product getProduct(String ref) {
        return productRepository.findByRef(ref)
                .orElseThrow(() -> new ProductNotFoundException("No product was stored with ref " + ref));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        String ref = ReferenceGenerator.genStringRef();
        product.setRef(ref);
        return productRepository.save(product);
    }

}