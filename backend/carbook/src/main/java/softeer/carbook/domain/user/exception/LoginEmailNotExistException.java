package softeer.carbook.domain.user.exception;

public class LoginEmailNotExistException extends RuntimeException{
    public LoginEmailNotExistException() {
        super("ERROR: Email not exist");
    }
}
