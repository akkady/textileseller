package ma.akkady.textileseller.controllers;

import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Test
    public void testGetProduct() throws Exception {
        // Mock product
        Product product = new Product();
        product.setRef("123");
        product.setName("Test Product");

        // Mock service
        when(productService.getProduct("123")).thenReturn(product);

        // Mock MVC
        mockMvc.perform(MockMvcRequestBuilders.get("/products/123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ref").value("123"))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testGetAll() throws Exception {
        // Mock products
        Product product1 = new Product();
        product1.setRef("123");
        product1.setName("Test Product 1");

        Product product2 = new Product();
        product2.setRef("456");
        product2.setName("Test Product 2");

        List<Product> products = Arrays.asList(product1, product2);

        // Mock service
        when(productService.getAll()).thenReturn(products);

        // Mock MVC
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ref").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ref").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test Product 2"));
    }

    @Test
    public void testCreate() throws Exception {
        // Mock product
        Product product = new Product();
        product.setRef("123");
        product.setName("Test Product");

        // Mock service
        when(productService.create(product)).thenReturn(product);

        // Mock MVC
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ref\":\"123\",\"name\":\"Test Product\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ref").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andReturn();

        // Verify response
        String content = result.getResponse().getContentAsString();
        assertEquals("{\"ref\":\"123\",\"name\":\"Test Product\"}", content);
    }

    @Test
    public void testFindProductsByName() throws Exception {
        // Mock products
        Product product1 = new Product();
        product1.setRef("123");
        product1.setName("Test Product 1");

        Product product2 = new Product();
        product1.setRef("abcd");
        product1.setName("Test Product 2");
        List<Product> products = List.of(product1, product2);
        given(productService.findProductsByName("Test")).willReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search?name=Test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ref").value(products.get(0).getRef()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(products.get(0).getName()));
    }
}