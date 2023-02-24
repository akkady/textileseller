package ma.akkady.textileseller.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.dtos.VendorSubscriptionRequest;
import ma.akkady.textileseller.services.VendorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendors")
@Api(tags = "Vendors")
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/subscribe")
    @ApiOperation(value = "Subscribe to the vendor service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vendor subscription successful"),
            @ApiResponse(code = 400, message = "Invalid vendor information provided"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public VendorInfoDto subscribe(@RequestBody @Valid VendorInfoDto vendorDto) {
        return vendorService.create(vendorDto);
    }

    @PostMapping("/password")
    @ApiOperation(value = "Create or update vendor password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vendor password created or updated successfully"),
            @ApiResponse(code = 400, message = "Invalid vendor subscription request provided"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public void createOrUpdatePassword(@RequestBody @Valid VendorSubscriptionRequest vendorSubscription) {
        vendorService.createOrUpdatePwd(vendorSubscription);
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "Get vendor information by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vendor information retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid username provided"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public VendorInfoDto getVendor(@PathVariable("username") @NotBlank String username) {
        return vendorService.getVendor(username);
    }
}

