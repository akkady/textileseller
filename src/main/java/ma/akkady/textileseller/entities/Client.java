package ma.akkady.textileseller.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends User {
    @Column(unique = true)
    private String code;
    public Client(String code, String name, String phone, String address) {
        super(null, name, phone, address);
    }
}
