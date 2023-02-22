package ma.akkady.textileseller.dtos;

import lombok.Data;

@Data
public class VendorSubscriptionRequest {
    private String username;
    private String newPwd;
    private String pwdConfirmation;
    private String oldPwd;
}
