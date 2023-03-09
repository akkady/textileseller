package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceSequence")
    @SequenceGenerator(name = "invoiceSequence")
    @Column(name = "id")
    private Long id;
    @Column(nullable = false,unique = true)
    private String ref;
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    @ManyToOne
    private Vendor vendor;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private Set<InvoiceEntry> entries;


}
