package ma.akkady.textileseller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.akkady.textileseller.entities.Client;
import ma.akkady.textileseller.entities.Currency;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceToDisplayDto {
    private Long id;
    private String invoiceRef;
    private Client client;
    private VendorInfoDto vendor;
    private Currency currency;
    private Map<String,List<Double>> entries;
}
