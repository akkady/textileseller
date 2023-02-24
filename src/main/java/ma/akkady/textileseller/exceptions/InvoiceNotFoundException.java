package ma.akkady.textileseller.exceptions;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException() {
        super("Invoice not found.");
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }

    public InvoiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
