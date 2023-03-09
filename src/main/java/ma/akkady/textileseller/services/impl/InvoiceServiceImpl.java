package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.entities.*;
import ma.akkady.textileseller.exceptions.InvoiceNotFoundException;
import ma.akkady.textileseller.exceptions.UserNotFoundException;
import ma.akkady.textileseller.mappers.InvoiceEntryMapper;
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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;
    private final InvoiceEntryRepository entryRepository;
    private final ClientRepository clientRepository;
    private final VendorRepository vendorRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceEntryMapper entryMapper;

    @Override
    public InvoiceToDisplayDto init(String clientCode, Long vendorId) {
        log.info("Initializing invoice for client {}", clientCode);
        String invoiceReference = ReferenceGenerator.genNumeric();
        Client client = clientRepository.findByCode(clientCode).orElseThrow(UserNotFoundException::new);
        Vendor vendor = vendorRepository.getReferenceById(vendorId);

        Invoice invoice = new Invoice();
        invoice.setRef(invoiceReference);
        invoice.setClient(client);
        invoice.setVendor(vendor);

        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDisplayedDto(invoice);
    }


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

    @Override
    public InvoiceEntryDto addEntry(InvoiceEntryDto entryDto) {
        log.info("Adding entry with value {} to invoice with id {}", entryDto.getEntry(), entryDto.getInvoiceId());
//        Product product = productService.getProductById(entryDto.getProductId());
//        Invoice invoice = invoiceRepository.findById(entryDto.getInvoiceId())
//                .orElseThrow(InvoiceNotFoundException::new);

        InvoiceEntry entry = entryMapper.toEntity(entryDto);

        entry = entryRepository.save(entry);

        return entryMapper.toDto(entry);
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
