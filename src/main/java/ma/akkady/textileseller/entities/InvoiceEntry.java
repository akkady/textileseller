package ma.akkady.textileseller.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "invoice_entries")

public class InvoiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entrySequence")
    @SequenceGenerator(name = "entrySequence")
    private Long id;
    private Double entry;
    @ManyToOne
    @JsonManagedReference
    private Invoice invoice;
    @ManyToOne
    private Product product;
}
