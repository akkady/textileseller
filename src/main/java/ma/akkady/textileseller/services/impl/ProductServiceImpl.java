package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.exceptions.ProductNotFoundException;
import ma.akkady.textileseller.repositories.ProductRepository;
import ma.akkady.textileseller.services.ProductService;
import ma.akkady.textileseller.utils.ReferenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author younes akkad
 */
@Service @Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Override
    public List<Product> findProductsByName(String name) {
        log.info("Retrieve products by name containing {}",name);
        return productRepository.findByNameContaining(name);
    }


    @Override
    public Product getByIdOrThrow(String ref) {
        log.info("Retrieve product by ref {}",ref);
        return productRepository.findByRef(ref)
                .orElseThrow(() -> new ProductNotFoundException("No product was stored with ref " + ref));
    }

    @Override
    public Product getByIdOrThrow(Long id) {
        log.info("Retrieve product by id {}",id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product was stored with id : " + id));
    }

    @Override
    public List<Product> getAll() {
        log.info("Retrieve all products");
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        log.info("Creating product {}",product);
        String ref = ReferenceGenerator.genStringRef();
        product.setRef(ref);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        log.info("Updating product with reference {}",product.getRef());
        getByIdOrThrow(product.getId());
        return productRepository.save(product);
    }
}