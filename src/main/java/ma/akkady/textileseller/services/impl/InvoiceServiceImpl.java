package ma.akkady.textileseller.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.akkady.textileseller.dtos.InitInvoiceDto;
import ma.akkady.textileseller.dtos.InvoiceCurrencyDto;
import ma.akkady.textileseller.dtos.InvoiceEntryDto;
import ma.akkady.textileseller.dtos.InvoiceToDisplayDto;
import ma.akkady.textileseller.entities.*;
import ma.akkady.textileseller.exceptions.InvoiceNotFoundException;
import ma.akkady.textileseller.mappers.InvoiceEntryMapper;
import ma.akkady.textileseller.mappers.InvoiceMapper;
import ma.akkady.textileseller.repositories.ClientRepository;
import ma.akkady.textileseller.repositories.InvoiceEntryRepository;
import ma.akkady.textileseller.repositories.InvoiceRepository;
import ma.akkady.textileseller.repositories.VendorRepository;
import ma.akkady.textileseller.services.InvoiceService;
import ma.akkady.textileseller.utils.ReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceEntryRepository entryRepository;
    private final ClientRepository clientRepository;
    private final VendorRepository vendorRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceEntryMapper entryMapper;

    @Override
    public InvoiceToDisplayDto init(InitInvoiceDto initInvoiceDto) {
        log.info("Initializing invoice with client id {} ", initInvoiceDto.getClientId());
        Client client = clientRepository.getReferenceById(initInvoiceDto.getClientId());
        Vendor vendor = vendorRepository.getReferenceById(initInvoiceDto.getVendorId());

        String invoiceReference = ReferenceGenerator.genNumeric();

        Invoice invoice = new Invoice();
        invoice.setRef(invoiceReference);
        invoice.setVendor(vendor);
        invoice.setClient(client);

        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDisplayedDto(invoice);
    }

    @Override
    public Invoice getByIdOrThrow(Long id) {
        log.info("Retrieving invoice with id {}", id);
        return invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);
    }


    @Override
    public InvoiceCurrencyDto changeCurrency(InvoiceCurrencyDto invoiceCurrency) {
        log.info("Setting currency {} for invoice with id {}", invoiceCurrency.getCurrency(), invoiceCurrency.getInvoiceId());
        Invoice invoice = getByIdOrThrow(invoiceCurrency.getInvoiceId());
        invoice.setCurrency(invoiceCurrency.getCurrency());

        return invoiceCurrency;
    }

    @Override
    public InvoiceToDisplayDto create(InvoiceToDisplayDto invoice) {
        log.info("Initializing invoice for client {}", invoice.getClient().getCode());
        String invoiceReference = ReferenceGenerator.genNumeric();
        Invoice invoice1 = invoiceMapper.toEntity(invoice);
        invoice1.setRef(invoiceReference);
        return invoiceMapper.toDisplayedDto(invoiceRepository.save(invoice1));
    }

    @Override
    public InvoiceEntryDto addEntry(InvoiceEntryDto entryDto) {
        log.info("Adding entry with value {} to invoice with id {}", entryDto.getEntry(), entryDto.getInvoiceId());

        InvoiceEntry entry = entryMapper.toEntity(entryDto);

        entry = entryRepository.save(entry);

        return entryMapper.toDto(entry);
    }

    @Override
    public InvoiceToDisplayDto getInvoice(String invoiceRef) {
        log.info("Retrieving invoice with reference {}", invoiceRef);
        return invoiceRepository.findByRef(invoiceRef)
                .map(invoiceMapper::toDisplayedDto)
                .orElseThrow(InvoiceNotFoundException::new);
    }

    @Override
    public List<String> getInvoiceByClient(String clientCode) {
        log.info("Retrieving invoices for client with code {}", clientCode);
        return invoiceRepository.findByClientCode(clientCode)
                .stream().map(Invoice::getRef)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceToDisplayDto> getInvoiceByVendor(String username) {
        log.info("Retrieving invoices for vendor with username {}", username);
        return invoiceMapper.toDisplayedDtos(invoiceRepository.findByVendorUsername(username));
    }

    @Override
    public void delete(String invoiceRef) {
        log.info("Deleting invoice with reference {}", invoiceRef);
        invoiceRepository.deleteByRef(invoiceRef);
    }

}
