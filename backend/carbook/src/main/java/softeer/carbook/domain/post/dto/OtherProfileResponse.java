package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class OtherProfileResponse {
    private final boolean isMyProfile = false;
    private String nickname;
    private String email;
    private boolean isFollow;
    private int follower;
    private int following;
    private List<Image> images;

    public OtherProfileResponse(OtherProfileResponseBuilder otherProfileResponseBuilder) {
        this.nickname = otherProfileResponseBuilder.nickname;
        this.email = otherProfileResponseBuilder.email;
        this.isFollow = otherProfileResponseBuilder.isFollow;
        this.follower = otherProfileResponseBuilder.follower;
        this.following = otherProfileResponseBuilder.following;
        this.images = otherProfileResponseBuilder.images;
    }

    public static class OtherProfileResponseBuilder {
        private String nickname;
        private String email;
        private boolean isFollow;
        private int follower;
        private int following;
        private List<Image> images;

        public OtherProfileResponseBuilder() {
        }

        public OtherProfileResponseBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public OtherProfileResponseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public OtherProfileResponseBuilder setFollow(boolean follow) {
            isFollow = follow;
            return this;
        }

        public OtherProfileResponseBuilder setFollower(int follower) {
            this.follower = follower;
            return this;
        }

        public OtherProfileResponseBuilder setFollowing(int following) {
            this.following = following;
            return this;
        }

        public OtherProfileResponseBuilder setImages(List<Image> images) {
            this.images = images;
            return this;
        }

        public OtherProfileResponse build(){
            return new OtherProfileResponse(this);
        }

    }
}
