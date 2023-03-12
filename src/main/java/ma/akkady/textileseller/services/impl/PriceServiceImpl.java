package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.exceptions.PriceNotFoundException;
import ma.akkady.textileseller.repositories.PriceRepository;
import ma.akkady.textileseller.services.PriceService;
import ma.akkady.textileseller.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author younes akkad
 */
@Service @Transactional
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final PriceRepository priceRepository;
    private final ProductService productService;

    @Override
    public List<Currency> getCurrencies() {
        return Arrays.asList(Currency.values());
    }

    @Override
    public Price getByIdOrThrow(Long id) {
        return priceRepository.findById(id).orElseThrow(PriceNotFoundException::new);
    }

    @Override
    public Set<Price> getPricesByProductRef(String ref) {
        log.info("Retrieve Price by product reference {}",ref);
        return priceRepository.findByProductRef(ref).orElse(Collections.emptySet());
    }

    @Override
    public Price createForProduct(Price price, String productRef) {
        log.info("Create price {} for product with reference {}",price,productRef);
        Assert.notNull(productRef, "Make sure to provide a valid product reference");
        Assert.notNull(price.getCurrency(), "The currency should not be null");

        Product product = productService.getByIdOrThrow(productRef);
        price = priceRepository.save(price);
        price.setProduct(product);

        return price;
    }

    @Override
    public Price updateValue(Price price) {
        log.info("Update price with id {}",price.getId());
        Assert.notNull(price.getCurrency(), "The currency should not be null");
        Assert.notNull(price.getPriceValue(), "The value should not be null");
        getByIdOrThrow(price.getId());
        return priceRepository.save(price);
    }

    @Override
    public Price getPriceWithCurrencyForProduct(Currency currency, String productRef) {
        log.info("Retrieve price for product by reference {} and currency {}",productRef,currency);
        Product product = productService.getByIdOrThrow(productRef);

        return product.getPrices().stream()
                .filter(v -> v.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new PriceNotFoundException("This product has no price with this currency"));
    }
}

