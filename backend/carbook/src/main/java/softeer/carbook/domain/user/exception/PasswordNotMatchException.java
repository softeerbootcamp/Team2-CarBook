package softeer.carbook.domain.user.exception;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(){
        super("ERROR: Password not match");
    }
}
