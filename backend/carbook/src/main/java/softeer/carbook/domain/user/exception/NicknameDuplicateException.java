package softeer.carbook.domain.user.exception;

public class NicknameDuplicateException extends RuntimeException{
    public NicknameDuplicateException() {
        super("ERROR: Duplicated nickname");
    }
}
