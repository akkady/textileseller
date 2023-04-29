package ma.akkady.textileseller.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ma.akkady.textileseller.constants.MappingUrls.API_URL;
import static ma.akkady.textileseller.constants.MappingUrls.PRODUCTS;

@RestController
@RequestMapping( PRODUCTS.BASE_URL)
@RequiredArgsConstructor
@Api(value = "Product Management System", description = "Operations pertaining to products in Product Management System", tags = PRODUCTS.TAG)
public class ProductController {


    private final ProductService productService;

    @PostMapping
    @ApiOperation(value = "Create a new product", response = Product.class)
    public ResponseEntity<Product> create(@RequestBody @NotNull @ApiParam(value = "Product object to be created", required = true) Product product) {
        Product createdProduct = productService.create(product);
        return ResponseEntity.ok().body(createdProduct);
    }

    @PutMapping
    @ApiOperation(value = "Update product info", response = Product.class)
    public ResponseEntity<Product> update(@RequestBody @NotNull @ApiParam(value = "Product object to be updated", required = true) Product product) {
        Product createdProduct = productService.update(product);
        return ResponseEntity.ok().body(createdProduct);
    }

    @GetMapping(value = PRODUCTS.SEARCH_BY_ID)
    @ApiOperation(value = "Get product by id", response = Product.class)
    public ResponseEntity<Product> getProductById(@PathVariable @NotBlank @ApiParam(value = "Product id", required = true) Long id) {
        Product product = productService.getByIdOrThrow(id);
        return ResponseEntity.ok().body(product);
    }
    @GetMapping(value = PRODUCTS.SEARCH, params = "ref")
    @ApiOperation(value = "Get product by reference", response = Product.class)
    public ResponseEntity<Product> getProductByRef(@RequestParam("ref") @NotBlank @ApiParam(value = "Product reference", required = true) String ref) {
        Product product = productService.getByIdOrThrow(ref);
        return ResponseEntity.ok().body(product);
    }
    @GetMapping(value = PRODUCTS.SEARCH, params = "name")
    @ApiOperation(value = "Search products by name", response = List.class)
    public ResponseEntity<List<Product>> findProductsByName(@RequestParam("name") @ApiParam(value = "Product name", required = true) String name) {
        List<Product> products = productService.findProductsByName(name);
        return ResponseEntity.ok().body(products);
    }
    @GetMapping
    @ApiOperation(value = "View a list of all products", response = List.class)
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok().body(products);
    }
}

