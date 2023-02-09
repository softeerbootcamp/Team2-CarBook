package softeer.carbook.domain.follow.dto;

public class ModifyFollowInfoForm {
    private final String followingNickname;

    public ModifyFollowInfoForm(String followingNickname) {
        this.followingNickname = followingNickname;
    }

    public String getFollowingNickname() {
        return followingNickname;
    }
}
