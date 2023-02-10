package softeer.carbook.domain.follow.model;

public class Follow {
    private final int follower_id;
    private final int following_id;

    public Follow(int follower_id, int following_id) {
        this.follower_id = follower_id;
        this.following_id = following_id;
    }

    public int getFollower_id() {
        return follower_id;
    }

    public int getFollowing_id() {
        return following_id;
    }
}
