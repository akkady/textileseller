package ma.akkady.textileseller.controllers;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequest;
import ma.akkady.textileseller.services.VendorService;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/vendors")
public class VendorController {
    private final VendorService vendorService;

    @PostMapping("/subscribe")
    public VendorInfoDto subscribe(@RequestBody VendorInfoDto vendorDto) {
        return vendorService.create(vendorDto);
    }

    @PostMapping("/password")
    public void createOrUpdatePassword(@RequestBody VendorSubscriptionRequest vendorSubreption) throws IllegalAccessException {
        vendorService.createOrUpdatePwd(vendorSubreption);
    }

    @GetMapping("/{username}")
    public VendorInfoDto getVendor(@PathVariable String username) {
        return vendorService.getVendor(username);
    }

}
