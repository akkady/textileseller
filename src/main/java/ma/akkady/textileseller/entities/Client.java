package ma.akkady.textileseller.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "clients")
public class Client extends User {
    @Column(unique = true)
    private String code;
    @OneToMany(mappedBy = "client")
    private Set<Invoice> invoices;
    public Client(String code, String name, String phone, String address) {
        super(null, name, phone, address);
    }
}
