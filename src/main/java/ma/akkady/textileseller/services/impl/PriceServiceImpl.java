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
    public List<Price> getPricesByProductRef(String ref) {
        productService.getProduct(ref);
        return priceRepository.findByProductCode(ref)
                .orElseThrow(() -> new PriceNotFoundException("No prices were found for this product"));
    }

    @Override
    public Price createForProduct(Price price, String productRef) {
        Assert.notNull(productRef,"Make sure to provide a valid product reference");
        Assert.notNull(price.getValue(), "The value should not be null");
        Assert.notNull(price.getCurrency(),"The currency of should not be null");
        Product product = productService.getProduct(productRef);
        price.setProduct(product);
        return priceRepository.save(price);
    }

    @Override
    public Price updateValue(Price price) {
        Assert.notNull(price.getValue(), "The value should not be null");
        Assert.notNull(price.getCurrency(),"The currency of should not be null");
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

