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
public class InvoiceInitDto {
    private String productRef;
    private String clientCode;
    private Long vendorId;

}
