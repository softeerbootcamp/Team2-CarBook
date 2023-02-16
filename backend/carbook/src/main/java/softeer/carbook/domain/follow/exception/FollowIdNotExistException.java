package softeer.carbook.domain.follow.exception;

public class FollowIdNotExistException extends RuntimeException{
    public FollowIdNotExistException() {
        super("ERROR: FollowId not exist");
    }
}
