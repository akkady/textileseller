package ma.akkady.textileseller.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;
import ma.akkady.textileseller.services.PriceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Api(value = "Product Price API", tags = "Product Prices" )
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/currencies")
    @ApiOperation(value = "Get all available currencies", response = List.class)
    public List<String> getCurrencies() {
        List<Currency> currencies = priceService.getCurrencies();
        return currencies.stream()
                .map(Currency::name)
                .collect(Collectors.toList());
    }
    @GetMapping("/{productRef}")
    @ApiOperation(value = "Get prices for a product", response = List.class)
    public Set<Price> getPricesByProductRef(
            @ApiParam(value = "Reference of the product to retrieve prices for", required = true) @PathVariable String productRef) {
        return priceService.getPricesByProductRef(productRef);
    }
    @PostMapping("/{productRef}")
    @ApiOperation(value = "Create a new price for a product", response = Price.class)
    public Price createForProduct(
            @ApiParam(value = "Reference of the product to create a price for", required = true) @NotBlank @PathVariable String productRef,
            @ApiParam(value = "New price information", required = true) @NotNull @RequestBody Price price) {
        return priceService.createForProduct(price, productRef);
    }

    @PutMapping("/")
    @ApiOperation(value = "Update an existing price", response = Price.class)
    public Price updateValue(
            @ApiParam(value = "Price information to update", required = true) @RequestBody Price price) {
        return priceService.updateValue(price);
    }

    @GetMapping("/byCurrency")
    @ApiOperation(value = "Get a product price with a specific currency", response = Price.class)
    public Price getPriceWithCurrencyForProduct(
            @ApiParam(value = "Reference of the product to retrieve the price for", required = true) @NotBlank @RequestParam String productRef,
            @ApiParam(value = "Currency to retrieve the price for", required = true) @RequestParam Currency currency) {
        return priceService.getPriceWithCurrencyForProduct(currency, productRef);
    }
}
