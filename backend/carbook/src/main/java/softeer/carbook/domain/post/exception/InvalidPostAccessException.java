package softeer.carbook.domain.post.exception;

public class InvalidPostAccessException extends RuntimeException{
    public InvalidPostAccessException() {
        super("ERROR: Invalid Access");
    }
}
