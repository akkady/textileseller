package ma.akkady.textileseller.services;

import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    //InvoiceToDisplayDto init(String clientCode,Long vendorId);

    Invoice getByIdOrThrow(Long id);

    InvoiceCurrencyDto changeCurrency(InvoiceCurrencyDto invoiceCurrency);
    InvoiceToDisplayDto create(InvoiceToDisplayDto invoice);
    InvoiceEntryDto addEntry(InvoiceEntryDto invoiceEntry);

    InvoiceToDisplayDto getInvoice(String invoiceRef);
    List<InvoiceToDisplayDto> getInvoiceByClient(String clientCode);
    List<InvoiceToDisplayDto> getInvoiceByVendor(String username);

    void delete(String invoiceRef);

}
