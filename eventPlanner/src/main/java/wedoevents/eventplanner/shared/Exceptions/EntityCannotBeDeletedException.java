package wedoevents.eventplanner.shared.Exceptions;

public class EntityCannotBeDeletedException extends RuntimeException {
    public EntityCannotBeDeletedException(String message) {
        super(message);
    }
    public EntityCannotBeDeletedException() {}
}
