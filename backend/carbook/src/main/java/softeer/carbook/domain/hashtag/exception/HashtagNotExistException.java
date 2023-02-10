package softeer.carbook.domain.hashtag.exception;

public class HashtagNotExistException extends RuntimeException {
    public HashtagNotExistException() {
        super("ERROR: Hashtag not exist");
    }
}
