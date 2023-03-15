package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter @Setter @NoArgsConstructor
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
    private Currency currency = Currency.MAD;
    @ManyToOne
    private Vendor vendor;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private Set<InvoiceEntry> entries;


}
