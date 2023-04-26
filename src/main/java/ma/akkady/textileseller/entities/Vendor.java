package ma.akkady.textileseller.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "vendors")
public class Vendor extends User {

    private String medaUrl;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "vendor")
    private Set<Invoice> invoices;
    @OneToMany
    Set<Product> products;
    @ManyToMany(fetch = FetchType.EAGER)
    Collection<SecurityRole> appRoles = new ArrayList<>();
}
