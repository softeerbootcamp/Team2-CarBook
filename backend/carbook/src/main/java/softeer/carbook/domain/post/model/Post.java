package softeer.carbook.domain.post.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private final int userId;
    private Timestamp createDate;
    private Timestamp updateDate;
    private final String content;

    public Post(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Post(int id, int userId, Timestamp createDate, Timestamp updateDate, String content) {
        this.id = id;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.content = content;
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
}
