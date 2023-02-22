package ma.akkady.textileseller.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(name = "productSequence")
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String ref;
    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "priceId")
    private Set<Price> prices = new HashSet<>();
}
