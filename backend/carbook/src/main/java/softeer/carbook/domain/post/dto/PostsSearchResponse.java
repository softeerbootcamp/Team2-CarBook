package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class PostsSearchResponse {
    private List<Image> images;

    public PostsSearchResponse(PostsSearchResponseBuilder postsSearchResponseBuilder){
        this.images = postsSearchResponseBuilder.images;
    }

    public List<Image> getImages() {
        return images;
    }

    public static class PostsSearchResponseBuilder {
        private List<Image> images;

        public PostsSearchResponseBuilder() {
        }

        public PostsSearchResponseBuilder images(List<Image> images) {
            this.images = images;
            return this;
        }

        public PostsSearchResponse build() {
            return new PostsSearchResponse(this);
        }
    }
}
