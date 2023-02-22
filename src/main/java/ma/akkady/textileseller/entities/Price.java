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
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    @Column(nullable = false)
    private double value;
    @ManyToOne
    private Product product;
}
