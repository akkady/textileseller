package ma.akkady.textileseller.repositories;

import ma.akkady.textileseller.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByRef(String reference);

    List<Invoice> findByClientCode(String clientCode);
    List<Invoice> findByVendorUsername(String username);

    void deleteByRef(String invoiceRef);
}
