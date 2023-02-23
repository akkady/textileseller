package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.exceptions.PriceNotFoundException;
import ma.akkady.textileseller.repositories.PriceRepository;
import ma.akkady.textileseller.services.PriceService;
import ma.akkady.textileseller.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author younes akkad
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final ProductService productService;

    @Override
    public List<Currency> getCurrencies() {
        return Arrays.asList(Currency.values());
    }

    @Override
    public Set<Price> getPricesByProductRef(String ref) {
        productService.getProduct(ref);
        return productService.getProduct(ref).getPrices();
    }

    @Override
    public Price createForProduct(Price price, String productRef) {
        Assert.notNull(productRef, "Make sure to provide a valid product reference");
        Assert.notNull(price.getPriceValue(), "The value should not be null");
        Assert.notNull(price.getCurrency(), "The currency of should not be null");

        price = priceRepository.save(price);
        Product product = productService.getProduct(productRef);
        product.getPrices().add(price);
        productService.update(product);

        return price;
    }

    @Override
    public Price updateValue(Price price) {
        Assert.notNull(price.getPriceValue(), "The value should not be null");
        Assert.notNull(price.getCurrency(), "The currency of should not be null");
        return priceRepository.save(price);
    }

    @Override
    public Price getPriceWithCurrencyForProduct(Currency currency, String productRef) {
        Product product = productService.getProduct(productRef);
        return product.getPrices().stream()
                .filter(v -> v.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new PriceNotFoundException("This product has no price with this currency"));
    }
}

