package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.InvoiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceEntryRepository extends JpaRepository<InvoiceEntry,Long> {
}

