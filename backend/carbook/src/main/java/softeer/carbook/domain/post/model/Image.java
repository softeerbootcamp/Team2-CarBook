package softeer.carbook.domain.post.model;

public class Image {
    private final int postId;
    private final String imageUrl;

    public Image(int postId, String imageUrl) {
        this.postId = postId;
        this.imageUrl = imageUrl;
    }

    public int getPostId() {
        return postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
