package softeer.carbook.domain.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.like.service.LikeService;
import softeer.carbook.global.dto.Message;

@RestController
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    // 좋아요
    // 좋아요 취소
    @PostMapping("/post/like")
    public ResponseEntity<Message> modifyLikeInfo(
            @RequestBody
    ){

    }

}
