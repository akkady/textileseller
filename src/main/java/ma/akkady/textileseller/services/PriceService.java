package ma.akkady.textileseller.services;

import ma.akkady.textileseller.entities.Currency;
import ma.akkady.textileseller.entities.Price;

import java.util.List;

public interface PriceService {
    List<Currency> getCurrencies();

    List<Price> getPricesByProductCode(String code);

    Price createForProduct(Price price, String productCode);

    Price updateValue(Price);
}
