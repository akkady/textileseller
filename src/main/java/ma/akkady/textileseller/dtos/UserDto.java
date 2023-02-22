package ma.akkady.textileseller.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
}
