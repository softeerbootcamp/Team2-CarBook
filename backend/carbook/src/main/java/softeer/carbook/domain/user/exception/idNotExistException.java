package softeer.carbook.domain.user.exception;

public class idNotExistException extends RuntimeException {
    public idNotExistException(String message) {
        super(message);
    }
}
