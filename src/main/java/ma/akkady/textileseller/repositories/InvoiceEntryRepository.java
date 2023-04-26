package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.InvoiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceEntryRepository extends JpaRepository<InvoiceEntry,Long> {
    List<InvoiceEntry> findByInvoiceId(Long invoiceId);
}

