package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class GuestPostsResponse {
    private final boolean isLogin = false;
    private List<Image> images;

    public GuestPostsResponse(GuestPostsResponseBuilder guestPostsResponseBuilder){
        this.images = guestPostsResponseBuilder.images;
    }

    public static class GuestPostsResponseBuilder {
        private List<Image> images;

        public GuestPostsResponseBuilder() {
        }

        public GuestPostsResponse.GuestPostsResponseBuilder images(List<Image> images) {
            this.images = images;
            return this;
        }

        public GuestPostsResponse build() {
            return new GuestPostsResponse(this);
        }
    }

}
