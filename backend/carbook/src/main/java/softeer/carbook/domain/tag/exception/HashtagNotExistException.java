package softeer.carbook.domain.tag.exception;

public class HashtagNotExistException extends RuntimeException {
    public HashtagNotExistException() {
        super("ERROR: Hashtag not exist");
    }
}
