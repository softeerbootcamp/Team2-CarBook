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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
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
