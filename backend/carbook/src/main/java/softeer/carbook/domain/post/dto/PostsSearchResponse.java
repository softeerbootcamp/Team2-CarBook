package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class PostsSearchResponse {
    private List<Image> images;

    public PostsSearchResponse(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }
}
