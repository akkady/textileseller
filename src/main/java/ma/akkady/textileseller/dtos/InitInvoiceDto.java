package ma.akkady.textileseller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitInvoiceDto {
    private Long vendorId;
    private Long clientId;
}
