package softeer.carbook.domain.hashtag.model;

public class Hashtag {

    private int id;
    private final String tag;

    public Hashtag(String tag) {
        this.tag = tag;
    }

    public Hashtag(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }
}
