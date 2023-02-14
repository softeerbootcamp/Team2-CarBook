package softeer.carbook.domain.tag.model;

public class Type {
    private final int id;
    private final String tag;

    public Type(int id, String tag) {
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
