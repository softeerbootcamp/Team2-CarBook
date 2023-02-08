package softeer.carbook.domain.user.exception;

public class idNotExistException extends RuntimeException {
    public idNotExistException() {
        super("ERROR: ID not exist");
    }
}
