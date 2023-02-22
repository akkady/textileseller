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
    private long productId;
    @NotNull(message = "Invoice id should not be empty")
    private long invoiceId;
    @NotNull(message = "entry value should not be empty")
    private long entry;


}
