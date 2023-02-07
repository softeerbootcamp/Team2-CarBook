package softeer.carbook.domain.user.exception;

public class NicknameNotExistException extends RuntimeException{
    public NicknameNotExistException(String message) {
        super(message);
    }
}
