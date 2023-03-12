package ma.akkady.textileseller.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(name = "productSequence")
    private Long id;
    @Column(nullable = false, unique = true)
    private String ref;
    private String name;
    private String description;
    @OneToMany(mappedBy = "product")
    private Set<Price> prices;
//    @JsonIgnore
//    @OneToMany(mappedBy = "product")
//    private Set<InvoiceEntry> entries;
}
