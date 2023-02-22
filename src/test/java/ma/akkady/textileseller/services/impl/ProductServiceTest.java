package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testFindProductsByName() {
        List<Product> products = new ArrayList<>();
//        products.add(new Product(1L, "Product A", "10.0"));
//        products.add(new Product(2L, "Product B", 20.0));
//        products.add(new Product(3L, "Product C", 30.0));
        Mockito.when(productRepository.findByNameContaining("Product"))
                .thenReturn(products);
        List<Product> result = productService.findProductsByName("Product");
        // Verify that the correct products are returned
        assertEquals(3, result.size());
        assertEquals("Product A", result.get(0).getName());
        assertEquals("Product B", result.get(1).getName());
        assertEquals("Product C", result.get(2).getName());
    }
}