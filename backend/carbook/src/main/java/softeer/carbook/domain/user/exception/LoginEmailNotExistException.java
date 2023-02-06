package softeer.carbook.domain.user.exception;

public class LoginEmailNotExistException extends RuntimeException{
    public LoginEmailNotExistException(String message) {
        super(message);
    }
}
