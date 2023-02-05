package softeer.carbook.domain.user.exception;

public class SignUpNicknameDuplicateException extends RuntimeException{
    public SignUpNicknameDuplicateException(String message) {
        super(message);
    }
}
