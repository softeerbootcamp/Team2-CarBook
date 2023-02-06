package softeer.carbook.domain.user.exception;

public class SignupNicknameDuplicateException extends RuntimeException{
    public SignupNicknameDuplicateException(String message) {
        super(message);
    }
}
