package ma.akkady.textileseller.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Api(value = "Product Management System", description = "Operations pertaining to products in Product Management System")
public class ProductController {


    private final ProductService productService;

    @GetMapping("/{ref}")
    @ApiOperation(value = "Get product by reference ID", response = Product.class)
    public ResponseEntity<Product> getProduct(@PathVariable("ref") @NotNull @ApiParam(value = "Product reference ID", required = true) String ref) {
        Product product = productService.getProduct(ref);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all products", response = List.class)
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    @ApiOperation(value = "Create a new product", response = Product.class)
    public ResponseEntity<Product> create(@RequestBody @NotNull @ApiParam(value = "Product object to be created", required = true) Product product) {
        Product createdProduct = productService.create(product);
        return ResponseEntity.ok().body(createdProduct);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search products by name", response = List.class)
    public ResponseEntity<List<Product>> findProductsByName(@RequestParam("name") @ApiParam(value = "Product name", required = true) String name) {
        List<Product> products = productService.findProductsByName(name);
        return ResponseEntity.ok().body(products);
    }
}

