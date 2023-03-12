package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
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
