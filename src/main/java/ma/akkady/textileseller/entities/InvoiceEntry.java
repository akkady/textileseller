package ma.akkady.textileseller.entities;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_entries")
public class InvoiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entrySequence")
    @SequenceGenerator(name = "entrySequence")
    @Column(name = "id")
    private Long id;
    private long entry;
    @ManyToOne
    @JoinColumn(name = "invoiceId", referencedColumnName = "id", nullable = false)
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false)
    private Product product;
}