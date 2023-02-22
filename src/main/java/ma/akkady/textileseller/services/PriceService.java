package ma.akkady.textileseller.services;

import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;

import java.util.List;

public interface PriceService {
    List<Currency> getCurrencies();

    List<Price> getPricesByProductRef(String ref);

    Price createForProduct(Price price, String productRef);

    Price updateValue(Price price);

    Price getPriceWithCurrencyForProduct(Currency currency, String productRef);

}
