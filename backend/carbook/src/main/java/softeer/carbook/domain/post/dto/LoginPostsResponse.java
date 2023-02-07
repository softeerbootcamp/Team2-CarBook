package softeer.carbook.domain.post.dto;

import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class LoginPostsResponse {
    private boolean isLogin;
    private String nickname;
    private List<Image> images;

    public LoginPostsResponse(boolean isLogin, String nickname, List<Image> images){
        this.isLogin = isLogin;
        this.nickname = nickname;
        this.images = images;
    }

}
