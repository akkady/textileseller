package ma.akkady.textileseller.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends User {
    private String code;

}
