package softeer.carbook.domain.user.exception;

public class NicknameNotExistException extends RuntimeException{
    public NicknameNotExistException() {
        super("ERROR: Nickname not exist");
    }
}
