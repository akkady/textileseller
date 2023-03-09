package ma.akkady.textileseller.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
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
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<InvoiceEntry> entries;
}
