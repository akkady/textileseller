package ma.akkady.textileseller.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    protected String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
}
