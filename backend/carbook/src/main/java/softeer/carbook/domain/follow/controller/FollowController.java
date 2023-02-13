package softeer.carbook.domain.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softeer.carbook.domain.follow.dto.FollowListResponse;
import softeer.carbook.domain.follow.dto.ModifyFollowInfoForm;
import softeer.carbook.domain.follow.service.FollowService;
import softeer.carbook.global.dto.Message;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    @Autowired
    public FollowController(
            FollowService followService,
            UserService userService
    ) {
        this.followService = followService;
        this.userService = userService;
    }

    // 프로필 페이지에서
        // 팔로워, 팔로잉 정보 받기 > follow 로


    // 팔로우, 언팔로우 기능 구현 ( 타인 프로필 페이지 ) > follow 로
    @PostMapping("/profile/follow")
    public ResponseEntity<Message> modifyFollowInfo(
            @RequestBody @Valid ModifyFollowInfoForm modifyFollowInfoForm,
            HttpServletRequest httpServletRequest
    ){
        // 일단 로그인 확인 후 로그인한 유저 받아오기
        User loginUser = userService.findLoginedUser(httpServletRequest);

        // 로그인한 유저 정보와 프로필 페이지 유저의 닉네임을 파라미터로 전달
        Message resultMsg = followService.modifyFollowInfo(
                loginUser, modifyFollowInfoForm.getFollowingNickname());
        return Message.make200Response(resultMsg.getMessage());
    }

        // 팔로워 리스트 조회 > follow 로
    @GetMapping("/profile/followers")
    public ResponseEntity<FollowListResponse> getFollowers(@RequestParam String nickname){
        return new ResponseEntity<>(followService.getFollowers(nickname), HttpStatus.OK);
    }

    @GetMapping("/profile/followings")
    public ResponseEntity<FollowListResponse> getFollowings(@RequestParam String nickname){
        return new ResponseEntity<>(followService.getFollowings(nickname), HttpStatus.OK);
    }

    @DeleteMapping("profile/follower")
    public ResponseEntity<Message> deleteFollower(@RequestParam String follower, HttpServletRequest httpServletRequest){
        User loginUser = userService.findLoginedUser(httpServletRequest);
        Message resultMsg = followService.deleteFollower(loginUser, follower);
        return Message.make200Response(resultMsg.getMessage());
    }


}
