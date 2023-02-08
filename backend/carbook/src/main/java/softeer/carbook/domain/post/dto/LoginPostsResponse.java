package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class LoginPostsResponse {
    private final boolean isLogin = true;
    private String nickname;
    private List<Image> images;

    public LoginPostsResponse(LoginPostsResponse.LoginPostsResponseBuilder loginPostsResponseBuilder){
        this.nickname = loginPostsResponseBuilder.nickname;
        this.images = loginPostsResponseBuilder.images;
    }

    public static class LoginPostsResponseBuilder {
        private String nickname;
        private List<Image> images;

        public LoginPostsResponseBuilder() {
        }

        public LoginPostsResponse.LoginPostsResponseBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public LoginPostsResponse.LoginPostsResponseBuilder images(List<Image> images) {
            this.images = images;
            return this;
        }

        public LoginPostsResponse build() {
            return new LoginPostsResponse(this);
        }
    }


}
