package softeer.carbook.domain.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.like.dto.ModifyLikeInfoForm;
import softeer.carbook.domain.like.service.LikeService;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;
import softeer.carbook.global.dto.Message;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @Autowired
    public LikeController(
            LikeService likeService,
            UserService userService
    ){
        this.likeService = likeService;
        this.userService = userService;
    }

    // 좋아요
    // 좋아요 취소
    @PostMapping("/post/like")
    public ResponseEntity<Message> modifyLikeInfo(
            @RequestBody ModifyLikeInfoForm modifyLikeInfoForm,
            HttpServletRequest httpServletRequest
    ){
        // 일단 로그인 확인 후 로그인한 유저 받아오기
        User loginUser = userService.findLoginedUser(httpServletRequest);

        // 로그인한 유저 정보와 좋아요/좋아요취소를 할 게시글의 id를 파라미터로 전달
        Message resultMsg = likeService.modifyLikeInfo(
                loginUser,
                httpServletRequest
        );
        return Message.make200Response(resultMsg.getMessage());

    }

}
