package ma.akkady.textileseller.entities;

import javax.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "vendors")
public class Vendor extends User {

    private String medaUrl;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany
    @JoinColumn(name = "invoiceId",referencedColumnName = "id")
    private Set<Invoice> invoices = new HashSet<>();

}
