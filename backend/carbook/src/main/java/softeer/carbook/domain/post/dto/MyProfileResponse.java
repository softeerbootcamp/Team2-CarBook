package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class MyProfileResponse {
    private final boolean isMyProfile = true;
    private String nickname;
    private String email;
    private int follower;
    private int following;
    private List<Image> images;

    public MyProfileResponse(MyProfileResponseBuilder myProfileResponseBuilder) {
        this.nickname = myProfileResponseBuilder.nickname;
        this.email = myProfileResponseBuilder.email;
        this.follower = myProfileResponseBuilder.follower;
        this.following = myProfileResponseBuilder.following;
        this.images = myProfileResponseBuilder.images;
    }

    public boolean isMyProfile() {
        return isMyProfile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
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

    public static class MyProfileResponseBuilder{
        private String nickname;
        private String email;
        private int follower;
        private int following;
        private List<Image> images;

        public MyProfileResponseBuilder() {
        }

        public MyProfileResponseBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public MyProfileResponseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public MyProfileResponseBuilder setFollower(int follower) {
            this.follower = follower;
            return this;
        }

        public MyProfileResponseBuilder setFollowing(int following) {
            this.following = following;
            return this;
        }

        public MyProfileResponseBuilder setImages(List<Image> images) {
            this.images = images;
            return this;
        }

        public MyProfileResponse build(){
            return new MyProfileResponse(this);
        }
    }

}
