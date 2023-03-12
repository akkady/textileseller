package ma.akkady.textileseller.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    @SequenceGenerator(name = "userSequence")
    private Long id;
    private String name;
    private String phone;
    private String address;
}
