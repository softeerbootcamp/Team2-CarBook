package softeer.carbook.domain.follow.dto;

import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class FollowListResponse {
    private List<String> nicknames;

    public FollowListResponse() { nicknames = null; }

    public FollowListResponse(List<String> nicknames) { this.nicknames = nicknames; }

    public List<String> getNicknames() {
        return nicknames;
    }

}
