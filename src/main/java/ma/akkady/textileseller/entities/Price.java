package ma.akkady.textileseller.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priceSequence")
    @SequenceGenerator(name = "priceSequence")
    @Column(name = "id")
    @JsonIgnore
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    @Column(nullable = false)
    private double priceValue;
    @ManyToOne
    private Product product;
}
