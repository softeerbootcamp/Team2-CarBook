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

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
