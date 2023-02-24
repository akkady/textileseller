package ma.akkady.textileseller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.akkady.textileseller.entities.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceCurrencyDto {
    private Long invoiceId;
    private Currency currency;
    private String initProductRef;
    private boolean isInitProductPricePresent = false;
}
