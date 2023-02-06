package softeer.carbook.domain.hashtag.exception;

public class HashtagNotExistException extends RuntimeException{
    public HashtagNotExistException(String message) {
        super(message);
    }
}
