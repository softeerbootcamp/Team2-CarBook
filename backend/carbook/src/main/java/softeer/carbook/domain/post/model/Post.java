package softeer.carbook.domain.post.model;

import java.sql.Timestamp;

public class Post {

    private int id;
    private final int userId;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String content;
    private int model_id;

    public Post(int userId, String content, int model_id) {
        this.userId = userId;
        this.content = content;
        this.model_id = model_id;
    }

    public Post(int id, int userId, Timestamp createDate, Timestamp updateDate, String content, int model_id) {
        this.id = id;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.content = content;
        this.model_id = model_id;
    }

    public int getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public String getContent() {
        return content;
    }

    public int getModelId() { return model_id; }
}
