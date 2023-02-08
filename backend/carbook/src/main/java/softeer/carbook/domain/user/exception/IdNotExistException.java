package softeer.carbook.domain.user.exception;

public class IdNotExistException extends RuntimeException {
    public IdNotExistException() {
        super("ERROR: ID not exist");
    }
}
