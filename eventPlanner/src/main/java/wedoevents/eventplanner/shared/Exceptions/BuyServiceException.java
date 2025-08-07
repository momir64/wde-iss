package wedoevents.eventplanner.shared.Exceptions;

public class BuyServiceException extends RuntimeException {
    public BuyServiceException(String message) {
        super(message);
    }
    public BuyServiceException() {}
}
