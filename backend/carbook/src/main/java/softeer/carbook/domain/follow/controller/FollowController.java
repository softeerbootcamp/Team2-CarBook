package softeer.carbook.domain.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.follow.dto.ModifyFollowInfoForm;
import softeer.carbook.domain.follow.service.FollowService;
import softeer.carbook.domain.user.dto.Message;

import javax.validation.constraints.Positive;

@RestController
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 프로필 페이지에서
        // 팔로워, 팔로잉 정보 받기 > follow 로

        // 팔로우, 언팔로우 기능 구현 ( 타인 프로필 페이지 ) > follow 로
    @PostMapping("/profile/follow")
    public ResponseEntity<Message> modifyFollowInfo(
            @RequestBody ModifyFollowInfoForm modifyFollowInfoForm
    ){
        Message resultMsg = followService.modifyFollowInfo(modifyFollowInfoForm);
        return Message.make200Response(resultMsg.getMessage());
    }

        // 팔로워 리스트 조회 > follow 로

        // 팔로잉 리스트 조회 > follow 로

}
