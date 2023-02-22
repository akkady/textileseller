package ma.akkady.textileseller.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {
        super("Price not found.");
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
