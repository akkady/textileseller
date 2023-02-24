package ma.akkady.textileseller.services;

import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceInitDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;

import java.util.List;

public interface InvoiceService {
    InvoiceToDisplayDto init(InvoiceInitDto initDto);

    InvoiceCurrencyDto chooseCurrency(InvoiceCurrencyDto invoiceCurrency);

    InvoiceEntryDto addEntry(InvoiceEntryDto invoiceEntry);

    InvoiceToDisplayDto getInvoice(String invoiceRef);
    List<InvoiceToDisplayDto> getInvoiceByClient(String clientCode);
    List<InvoiceToDisplayDto> getInvoiceByVendor(String username);

    void delete(String invoiceRef);

}
