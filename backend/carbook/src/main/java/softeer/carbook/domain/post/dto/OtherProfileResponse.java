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

    public boolean isMyProfile() {
        return isMyProfile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public int getFollower() {
        return follower;
    }

    public int getFollowing() {
        return following;
    }

    public List<Image> getImages() {
        return images;
    }

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

        public OtherProfileResponseBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public OtherProfileResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public OtherProfileResponseBuilder follow(boolean follow) {
            isFollow = follow;
            return this;
        }

        public OtherProfileResponseBuilder follower(int follower) {
            this.follower = follower;
            return this;
        }

        public OtherProfileResponseBuilder following(int following) {
            this.following = following;
            return this;
        }

        public OtherProfileResponseBuilder images(List<Image> images) {
            this.images = images;
            return this;
        }

        public OtherProfileResponse build(){
            return new OtherProfileResponse(this);
        }

    }
}
