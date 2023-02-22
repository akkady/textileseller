package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
