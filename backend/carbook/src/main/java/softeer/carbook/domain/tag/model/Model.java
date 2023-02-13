package softeer.carbook.domain.tag.model;

public class Model {
    private final int id;
    private final int typeId;
    private final String tag;

    public Model(int id, int typeId, String tag) {
        this.id = id;
        this.typeId = typeId;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTag() {
        return tag;
    }
}
