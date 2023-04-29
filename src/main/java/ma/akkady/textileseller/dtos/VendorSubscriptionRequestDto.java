package ma.akkady.textileseller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorSubscriptionRequestDto {
    private String username;
    private String newPwd;
    private String pwdConfirmation;
    private String oldPwd;
}
