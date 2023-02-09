package softeer.carbook.domain.follow.dto;

import javax.validation.constraints.NotBlank;

public class ModifyFollowInfoForm {
    @NotBlank
    private final String followingNickname;

    // json 파싱 문제 때문에 빈 생성자를 생성해 주어야 함..

    public ModifyFollowInfoForm() {
        followingNickname = null;
    }

    public ModifyFollowInfoForm(String followingNickname) {
        this.followingNickname = followingNickname;
    }

    public String getFollowingNickname() {
        return followingNickname;
    }
}
