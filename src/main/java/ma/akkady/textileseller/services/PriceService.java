package ma.akkady.textileseller.services;

import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;

import java.util.List;
import java.util.Set;

public interface PriceService {
    List<Currency> getCurrencies();

    Price getByIdOrThrow(Long id);

    Set<Price> getPricesByProductRef(String ref);

    Price createForProduct(Price price, String productRef);

    Price updateValue(Price price);

    Price getPriceWithCurrencyForProduct(Currency currency, String productRef);

}
