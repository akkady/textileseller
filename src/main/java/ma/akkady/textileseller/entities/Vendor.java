package ma.akkady.textileseller.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "vendors")
public class Vendor extends User {

    private String medaUrl;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "vendor")
    private Set<Invoice> invoices ;

}
