package softeer.carbook.domain.post.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.carbook.domain.post.model.Image;

import java.util.List;

public class GuestPostsResponse {
    private boolean isLogin;
    private List<Image> images;

    public GuestPostsResponse(boolean isLogin, List<Image> images){
        this.isLogin = isLogin;
        this.images = images;
    }

    /*
    public static ResponseEntity<GuestPostsResponse> make200Response(){
        return new ResponseEntity<>(
                new GuestPostsResponse(isLogin, images),
                HttpStatus.OK
        );
    }

     */

}
