package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;
import java.util.Map;

public class GuestPostsResponse {
    private boolean isLogin;
    private List<Image> images;

    public GuestPostsResponse(boolean isLogin, List<Image> images){
        this.isLogin = isLogin;
        this.images = images;
    }
}
