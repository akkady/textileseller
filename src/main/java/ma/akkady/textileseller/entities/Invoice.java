package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
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
    @JoinColumn(name="vendorId",referencedColumnName = "id")
    private Vendor vendor;
    @ManyToOne
    @JoinColumn(name="clientId",referencedColumnName = "id")
    private Client client;
    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceEntry> entries = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<Product> products = new HashSet<>();
}
