package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priceSequence")
    @SequenceGenerator(name = "priceSequence")
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    @Column(nullable = false)
    private double priceValue;

    @JsonIgnore
    @ManyToOne
    private Product product;
}
