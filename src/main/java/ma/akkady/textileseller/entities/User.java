package ma.akkady.textileseller.entities;

import javax.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    @SequenceGenerator(name = "userSequence")
    @Column(name = "id")
    private Long id;
    private String name;
    private String phone;
    private String address;
}
