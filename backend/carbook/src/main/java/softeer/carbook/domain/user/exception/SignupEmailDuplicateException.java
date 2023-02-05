package softeer.carbook.domain.user.exception;

public class SignupEmailDuplicateException extends RuntimeException{
    public SignupEmailDuplicateException(String message) {
        super(message);
    }
}
