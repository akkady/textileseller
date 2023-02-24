package ma.akkady.textileseller.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntryDto {

    @NotNull(message = "Product id should not be empty")
    private Long productId;
    @NotNull(message = "Invoice id should not be empty")
    private Long invoiceId;
    @NotNull(message = "entry value should not be empty")
    private Double entry;


}
