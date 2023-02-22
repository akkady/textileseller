package ma.akkady.textileseller.entities;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priceSequence")
    @SequenceGenerator(name = "priceSequence")
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @ManyToOne
    private Product product;
}
