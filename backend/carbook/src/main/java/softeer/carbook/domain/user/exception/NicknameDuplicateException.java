package softeer.carbook.domain.user.exception;

public class NicknameDuplicateException extends RuntimeException{
    public NicknameDuplicateException(String message) {
        super(message);
    }
}
