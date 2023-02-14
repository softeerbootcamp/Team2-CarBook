package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.tag.dto.TagSearchResult;

import java.util.List;

public class PostDetailResponse {
    private final boolean isMyPost;
    private final String nickname;
    private final String imageUrl;
    private final int like;
    private final String createDate;
    private final String updateDate;
    private final List<TagSearchResult> keywords;
    private final String content;

    public PostDetailResponse(PostDetailResponseBuilder postDetailResponseBuilder){
        this.isMyPost = postDetailResponseBuilder.isMyPost;
        this.nickname = postDetailResponseBuilder.nickname;
        this.imageUrl = postDetailResponseBuilder.imageUrl;
        this.like = postDetailResponseBuilder.like;
        this.createDate = postDetailResponseBuilder.createDate;
        this.updateDate = postDetailResponseBuilder.updateDate;
        this.keywords = postDetailResponseBuilder.keywords;
        this.content = postDetailResponseBuilder.content;
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

    public int getLike() {
        return like;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public List<TagSearchResult> getKeywords() {
        return keywords;
    }

    public String getContent() {
        return content;
    }

    public static class PostDetailResponseBuilder{
        private boolean isMyPost;
        private String nickname;
        private String imageUrl;
        private int like;
        private String createDate;
        private String updateDate;
        private List<TagSearchResult> keywords;
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

        public PostDetailResponseBuilder like(int like){
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

        public PostDetailResponseBuilder keywords(List<TagSearchResult> keywords){
            this.keywords = keywords;
            return this;
        }

        public PostDetailResponseBuilder content(String content){
            this.content = content;
            return this;
        }

        public PostDetailResponse build() { return new PostDetailResponse(this); }

    }

}
