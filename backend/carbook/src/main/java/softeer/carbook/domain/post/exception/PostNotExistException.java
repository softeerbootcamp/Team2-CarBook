package softeer.carbook.domain.post.exception;

public class PostNotExistException extends RuntimeException{
    public PostNotExistException() {
        super("ERROR: Post not exist");
    }
}
