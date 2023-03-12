package ma.akkady.textileseller.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "clients")
public class Client extends User {
    @Column(unique = true)
    private String code;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Invoice> invoices;
    public Client(String code, String name, String phone, String address) {
        super(null, name, phone, address);
        this.code = code;
    }
}
