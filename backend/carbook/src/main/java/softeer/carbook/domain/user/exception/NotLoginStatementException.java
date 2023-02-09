package softeer.carbook.domain.user.exception;

public class NotLoginStatementException extends RuntimeException{
    public NotLoginStatementException() {
        super("ERROR: Session Has Expired");
    }
}
