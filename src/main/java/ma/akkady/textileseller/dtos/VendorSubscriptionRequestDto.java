package ma.akkady.textileseller.dtos;

import lombok.Data;

@Data
public class VendorSubscriptionRequestDto {
    private String username;
    private String newPwd;
    private String pwdConfirmation;
    private String oldPwd;
}
