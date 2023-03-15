package ma.akkady.textileseller.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Api(value = "Invoice Management system", tags = "Invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    /*    @PostMapping("/init")
        @ApiOperation(value = "Initialize a new invoice", response = InvoiceToDisplayDto.class)
        public ResponseEntity<InvoiceToDisplayDto> init(@RequestParam("clientCode") @NotBlank String clientId ,@RequestParam("vendorId") @NotNull Long vendorId) {
            InvoiceToDisplayDto invoice = invoiceService.init(clientId,vendorId);
            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        }*/
    @PostMapping("/create")
    @ApiOperation(value = "Initialize a new invoice", response = InvoiceToDisplayDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Invoice initialized", response = InvoiceToDisplayDto.class), @ApiResponse(code = 400, message = "Invalid data")})
    public ResponseEntity<InvoiceToDisplayDto> create(@RequestBody InvoiceToDisplayDto invoiceDto) {
        InvoiceToDisplayDto invoice = invoiceService.create(invoiceDto);
        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
    }

    @PostMapping("/currency")
    @ApiOperation(value = "Choose currency for invoice", response = InvoiceCurrencyDto.class)
    public ResponseEntity<InvoiceCurrencyDto> chooseCurrency(
            @RequestBody @Valid InvoiceCurrencyDto invoiceCurrency
    ) {
        invoiceCurrency = invoiceService.changeCurrency(invoiceCurrency);
        return new ResponseEntity<>(invoiceCurrency, HttpStatus.OK);
    }

    @PostMapping("/entries")
    @ApiOperation(value = "Add an entry to an invoice", response = InvoiceEntryDto.class)
    public ResponseEntity<InvoiceEntryDto> addEntry(
            @RequestBody @Valid InvoiceEntryDto invoiceEntry
    ) {
        invoiceEntry = invoiceService.addEntry(invoiceEntry);
        return new ResponseEntity<>(invoiceEntry, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceRef}")
    @ApiOperation(value = "Get an invoice by reference", response = InvoiceToDisplayDto.class)
    public ResponseEntity<InvoiceToDisplayDto> getInvoice(
            @PathVariable String invoiceRef
    ) {
        InvoiceToDisplayDto invoice = invoiceService.getInvoice(invoiceRef);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @GetMapping(params = "clientCode")
    @ApiOperation(value = "Get invoices by client code", response = List.class)
    public ResponseEntity<List<InvoiceToDisplayDto>> getInvoicesByClient(
            @RequestParam String clientCode
    ) {
        List<InvoiceToDisplayDto> invoices = invoiceService.getInvoiceByClient(clientCode);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping(params = "vendorUsername")
    @ApiOperation(value = "Get invoices by vendor username", response = List.class)
    public ResponseEntity<List<InvoiceToDisplayDto>> getInvoicesByVendor(
            @RequestParam String vendorUsername
    ) {
        List<InvoiceToDisplayDto> invoices = invoiceService.getInvoiceByVendor(vendorUsername);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @DeleteMapping("/{invoiceRef}")
    @ApiOperation(value = "Delete an invoice by reference")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable String invoiceRef
    ) {
        invoiceService.delete(invoiceRef);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
