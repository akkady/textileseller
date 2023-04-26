package ma.akkady.textileseller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
    private Long id;
    protected String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String code;
}
