package softeer.carbook.domain.post.model;

import java.sql.Timestamp;

public class Post {

    private int id;
    private int userId;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String content;
    private int modelId;
    private int likeCount;

    public Post(int userId, String content, int modelId) {
        this.userId = userId;
        this.content = content;
        this.modelId = modelId;
    }

    public Post(int id, Timestamp updateDate, String content, int modelId){
        this.id = id;
        this.updateDate = updateDate;
        this.content = content;
        this.modelId = modelId;
    }

    public Post(
            int id, int userId,
            Timestamp createDate, Timestamp updateDate,
            String content, int modelId,
            int likeCount) {
        this.id = id;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.content = content;
        this.modelId = modelId;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public int getLikeCount() {
        return likeCount;
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

    public int getModelId() { return modelId; }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Post) {
            return this.id == ((Post) obj).getId();
        }
        return false;
    }
}
