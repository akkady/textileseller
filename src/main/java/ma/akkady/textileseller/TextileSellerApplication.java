package ma.akkady.textileseller;

import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequestDto;
import ma.akkady.textileseller.entities.SecurityRole;
import ma.akkady.textileseller.services.AccountService;
import ma.akkady.textileseller.services.VendorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class TextileSellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextileSellerApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner start(AccountService service, VendorService vendorService) {
//        return args -> {
//            VendorInfoDto vendor = VendorInfoDto.builder()
//                    .firstname("Supper")
//                    .lastname("Admin")
//                    .username("admin@me")
//                    .build();
//            vendorService.create(vendor);
//            vendorService.createOrUpdatePwd(VendorSubscriptionRequestDto.
//                    builder().newPwd("1234").pwdConfirmation("1234").username("admin@me")
//                    .build());
//
//            service.creteRole(new SecurityRole(null, "ADMIN"));
//            service.creteRole(new SecurityRole(null, "USER"));
//            service.addRoleToUser("admin@me", "ADMIN");
//        };
//    }

}
