package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceInitDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.entities.*;
import ma.akkady.textileseller.exceptions.InvoiceNotFoundException;
import ma.akkady.textileseller.mappers.InvoiceMapper;
import ma.akkady.textileseller.repositories.ClientRepository;
import ma.akkady.textileseller.repositories.InvoiceEntryRepository;
import ma.akkady.textileseller.repositories.InvoiceRepository;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.InvoiceService;
import ma.akkady.textileseller.services.ProductService;
import ma.akkady.textileseller.utils.ReferenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;
    private final InvoiceEntryRepository entryRepository;
    private final ClientRepository clientRepository;
    private final VendorRepository vendorRepository;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    @Override
    public InvoiceToDisplayDto init(InvoiceInitDto initDto) {
        log.info("Initializing invoice with product {} and client {}", initDto.getProductRef(), initDto.getClientCode());
        String invoiceReference = ReferenceGenerator.genNumeric();
        Product product = productService.getProduct(initDto.getProductRef());
        Client client = clientRepository.findByCode(initDto.getClientCode());
        Vendor vendor = vendorRepository.getReferenceById(initDto.getVendorId());

        Invoice invoice = Invoice.builder()
                .ref(invoiceReference)
                .products(Collections.singleton(product))
                .client(client)
                .vendor(vendor)
                .build();
        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDisplayedDto(invoice);
    }


    @Transactional
    @Override
    public InvoiceCurrencyDto chooseCurrency(InvoiceCurrencyDto invoiceCurrency) {
        log.info("Setting currency {} for invoice with id {}", invoiceCurrency.getCurrency(), invoiceCurrency.getInvoiceId());
        Invoice invoice = invoiceRepository.getReferenceById(invoiceCurrency.getInvoiceId());
        invoice.setCurrency(invoiceCurrency.getCurrency());

        boolean isProductPricePresent = productService.getProduct(invoiceCurrency.getInitProductRef())
                .getPrices()
                .stream()
                .anyMatch(price -> price.getCurrency().equals(invoiceCurrency.getCurrency()));
        invoiceCurrency.setInitProductPricePresent(isProductPricePresent);

        return invoiceCurrency;
    }


    @Transactional
    @Override
    public InvoiceEntryDto addEntry(InvoiceEntryDto invoiceEntry) {
        log.info("Adding entry with value {} to invoice with id {}", invoiceEntry.getEntry(), invoiceEntry.getInvoiceId());
        Product product = productService.getProductById(invoiceEntry.getProductId());
        Invoice invoice = invoiceRepository.findById(invoiceEntry.getInvoiceId())
                .orElseThrow(InvoiceNotFoundException::new);

        InvoiceEntry entry = InvoiceEntry.builder()
                .product(product)
                .invoice(invoice)
                .entry(invoiceEntry.getEntry())
                .build();
        entry = entryRepository.save(entry);
        invoice.getEntries().add(entry);

        return invoiceEntry;
    }

    @Override
    public InvoiceToDisplayDto getInvoice(String invoiceRef) {
        log.info("Retrieving invoice with reference {}",invoiceRef);
        return invoiceRepository.findByRef(invoiceRef)
                .map(invoiceMapper::toDisplayedDto)
                .orElseThrow(InvoiceNotFoundException::new);
    }

    @Override
    public List<InvoiceToDisplayDto> getInvoiceByClient(String clientCode) {
        log.info("Retrieving invoices for client with code {}",clientCode);
        return invoiceMapper.toDisplayedDtos(invoiceRepository.findByClientCode(clientCode));
    }

    @Override
    public List<InvoiceToDisplayDto> getInvoiceByVendor(String username) {
        log.info("Retrieving invoices for vendor with username {}",username);
        return invoiceMapper.toDisplayedDtos(invoiceRepository.findByVendorUsername(username));
    }

    @Override
    public void delete(String invoiceRef) {
        log.info("Deleting invoice with reference {}",invoiceRef);
        invoiceRepository.deleteByRef(invoiceRef);
    }

}
