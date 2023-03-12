package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("This is a test product");

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct.getId());
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getDescription(), createdProduct.getDescription());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("This is a test product");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.update(product);

        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getDescription(), updatedProduct.getDescription());

        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("This is a test product");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getByIdOrThrow(product.getId());

        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());

        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void testGetProductByRef() {
        String ref = "test-ref";

        Product product = new Product();
        product.setId(1L);
        product.setRef(ref);
        product.setName("Test Product");
        product.setDescription("This is a test product");

        when(productRepository.findByRef(ref)).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getByIdOrThrow(ref);

        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());

        verify(productRepository, times(1)).findByRef(ref);
    }

    @Test
    void testFindProductsByName() {
        String name = "Test";

        Product product1 = new Product();
        product1.setName("Test Product");
        product1.setDescription("This is a test product");

        Product product2 = new Product();
        product2.setName("Another Test Product");
        product2.setDescription("This is another test product");

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findByNameContaining(name)).thenReturn(products);

        List<Product> productsWithName = productService.findProductsByName(name);

        assertEquals(2, productsWithName.size());
        assertEquals(product1.getId(), productsWithName.get(0).getId());
        assertEquals(product1.getName(), productsWithName.get(0).getName());
        assertEquals(product1.getDescription(), productsWithName.get(0).getDescription());
        assertEquals(product2.getId(), productsWithName.get(1).getId());
        assertEquals(product2.getName(), productsWithName.get(1).getName());
        assertEquals(product2.getDescription(), productsWithName.get(1).getDescription());

        verify(productRepository, times(1)).findByNameContaining(name);
    }
}