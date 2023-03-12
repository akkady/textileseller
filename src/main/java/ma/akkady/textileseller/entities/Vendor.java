package ma.akkady.textileseller.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "vendors")
public class Vendor extends User {

    private String medaUrl;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "vendor")
    private Set<Invoice> invoices ;

}
