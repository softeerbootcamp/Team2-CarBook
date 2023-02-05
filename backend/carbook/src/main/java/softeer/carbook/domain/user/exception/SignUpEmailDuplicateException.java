package softeer.carbook.domain.user.exception;

public class SignUpEmailDuplicateException extends RuntimeException{
    public SignUpEmailDuplicateException(String message) {
        super(message);
    }
}
