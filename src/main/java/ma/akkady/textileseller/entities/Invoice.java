package ma.akkady.textileseller.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceSequence")
    @SequenceGenerator(name = "invoiceSequence")
    @Column(name = "id")
    private Long id;
    private String reference;
    private String currency;
    @ManyToOne
    @JoinColumn(name="vendorId",referencedColumnName = "id")
    private Vendor vendor;
    @ManyToOne
    @JoinColumn(name="clientId",referencedColumnName = "id")
    private Client client;
    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceEntry> entries = new HashSet<>();

}
