package ma.akkady.textileseller.exceptions;

public class PasswordConfirmationException extends RuntimeException {
    public PasswordConfirmationException() {
        super("Password confirmation error.");
    }
    public PasswordConfirmationException(String msg) {
        super(msg);
    }
    public PasswordConfirmationException(String msg,Throwable cause) {
        super(msg,cause);
    }

}
