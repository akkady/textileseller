package ma.akkady.textileseller.services.impl;

import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;
import ma.akkady.textileseller.entities.Product;
import ma.akkady.textileseller.exceptions.PriceNotFoundException;
import ma.akkady.textileseller.exceptions.ProductNotFoundException;
import ma.akkady.textileseller.repositories.PriceRepository;
import ma.akkady.textileseller.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void getCurrencies() {
        List<Currency> currencies = Arrays.asList(Currency.values());
        assertEquals(currencies, priceService.getCurrencies());
    }

    @Test
    void getPricesByProductRef_whenPricesExist() {
        String ref = "productRef";
        Product product = new Product();
        product.setRef(ref);
        Price price1 = new Price();
        price1.setPriceValue(10.0);
        price1.setCurrency(Currency.USD);
        Price price2 = new Price();
        price2.setPriceValue(20.0);
        price2.setCurrency(Currency.MAD);
        Set<Price> prices = Set.of(price1, price2);
        product.setPrices(prices);

        when(productService.getProduct(ref)).thenReturn(product);
        when(priceRepository.findByProductRef(ref)).thenReturn(Optional.of(prices));

        Set<Price> result1 = priceService.getPricesByProductRef(ref);
        Set<Price> result2 = productService.getProduct(ref).getPrices();
        assertEquals(result1, result2);

        verify(productService, times(1)).getProduct(ref);
        verify(priceRepository, times(1)).findByProductRef(ref);
    }

    @Test
    void getPricesByProductRef_whenProductDoesNotExist() {
        String ref = "productRef";

        when(productService.getProduct(ref)).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> priceService.getPricesByProductRef(ref));

        verify(productService, times(1)).getProduct(ref);
        verify(priceRepository, times(0)).findByProductRef(ref);
    }

    @Test
    void getPricesByProductRef_whenPricesDoNotExist() {
        String ref = "productRef";

        when(productService.getProduct(ref)).thenReturn(new Product());
        when(priceRepository.findByProductRef(ref)).thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> priceService.getPricesByProductRef(ref));

        verify(productService, times(1)).getProduct(ref);
        verify(priceRepository, times(1)).findByProductRef(ref);
    }

    @Test
    void createForProduct() {
        String ref = "productRef";
        Price price = new Price();
        price.setPriceValue(10.0);
        price.setCurrency(Currency.USD);
        Product product = new Product();
        product.setRef(ref);

        when(productService.getProduct(ref)).thenReturn(product);
        when(priceRepository.save(price)).thenReturn(price);

        Price result = priceService.createForProduct(price, ref);
        assertEquals(price, result);

        verify(productService, times(1)).getProduct(ref);
        verify(priceRepository, times(1)).save(price);
    }

    @Test
    void createForProduct_whenProductDoesNotExist() {
        String ref = "productRef";
        Price price = new Price();
        price.setPriceValue(10.0);
        price.setCurrency(Currency.USD);

        when(productService.getProduct(ref)).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> priceService.createForProduct(price, ref));

        verify(productService, times(1)).getProduct(ref);
    }
}