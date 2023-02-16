package softeer.carbook.domain.post.dto;

import java.util.List;

public class PostDetailResponse {
    private final boolean isMyPost;
    private final String nickname;
    private final String imageUrl;
    private final boolean isLike;
    private final int likeCount;
    private final String createDate;
    private final String updateDate;
    private final String type;
    private final String model;
    private final List<String> hashtags;
    private final String content;

    public PostDetailResponse(PostDetailResponseBuilder postDetailResponseBuilder){
        this.isMyPost = postDetailResponseBuilder.isMyPost;
        this.nickname = postDetailResponseBuilder.nickname;
        this.imageUrl = postDetailResponseBuilder.imageUrl;
        this.isLike = postDetailResponseBuilder.isLike;
        this.likeCount = postDetailResponseBuilder.like;
        this.createDate = postDetailResponseBuilder.createDate;
        this.updateDate = postDetailResponseBuilder.updateDate;
        this.type = postDetailResponseBuilder.type;
        this.model = postDetailResponseBuilder.model;
        this.hashtags = postDetailResponseBuilder.hashtags;
        this.content = postDetailResponseBuilder.content;
    }

    public boolean isLike() {
        return isLike;
    }

    public boolean isMyPost() {
        return isMyPost;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public String getContent() {
        return content;
    }

    public static class PostDetailResponseBuilder{
        private boolean isMyPost;
        private String nickname;
        private String imageUrl;
        private boolean isLike;
        private int like;
        private String createDate;
        private String updateDate;
        private String type;
        private String model;
        private List<String> hashtags;
        private String content;

        public PostDetailResponseBuilder(){}

        public PostDetailResponseBuilder isMyPost(boolean isMyPost){
            this.isMyPost = isMyPost;
            return this;
        }

        public PostDetailResponseBuilder nickname(String nickname){
            this.nickname = nickname;
            return this;
        }

        public PostDetailResponseBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public PostDetailResponseBuilder isLike(boolean isLike){
            this.isLike = isLike;
            return this;
        }

        public PostDetailResponseBuilder likeCount(int like){
            this.like = like;
            return this;
        }

        public PostDetailResponseBuilder createDate(String createDate){
            this.createDate = createDate;
            return this;
        }

        public PostDetailResponseBuilder updateDate(String updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public PostDetailResponseBuilder type(String type){
            this.type = type;
            return this;
        }

        public PostDetailResponseBuilder model(String model){
            this.model = model;
            return this;
        }

        public PostDetailResponseBuilder hashtags(List<String> hashtags){
            this.hashtags = hashtags;
            return this;
        }

        public PostDetailResponseBuilder content(String content){
            this.content = content;
            return this;
        }

        public PostDetailResponse build() { return new PostDetailResponse(this); }

    }

}
