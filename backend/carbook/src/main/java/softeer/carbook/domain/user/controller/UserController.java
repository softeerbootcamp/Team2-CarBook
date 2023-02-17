package softeer.carbook.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softeer.carbook.domain.user.dto.*;
import softeer.carbook.domain.user.service.UserService;
import softeer.carbook.global.dto.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody @Valid SignupForm signupForm){
        Message resultMsg = userService.signup(signupForm);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody @Valid LoginForm loginForm, HttpSession session) {
        Message resultMsg = userService.login(loginForm, session);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 로그아웃
    @PostMapping("/profile/logout")
    public ResponseEntity<Message> logout(HttpServletRequest httpServletRequest){
        Message resultMsg = userService.logout(httpServletRequest);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 로그인한 사용자인지
    @GetMapping("/isLogin")
    public ResponseEntity<IsLoginForm> isLogin(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(userService.getIsLoginState(httpServletRequest.getSession()), HttpStatus.OK);
    }

    // 닉네임 변경 ( 자신 프로필 페이지 )
    @PatchMapping("/profile/modify/{nickname}")
    public ResponseEntity<?> modifyNickname(
            @PathVariable("nickname") String nickname,
            @RequestBody @Valid ModifyNickNameForm modifyNickNameForm,
            HttpServletRequest httpServletRequest
    ){
        Message resultMsg = userService.modifyNickname(
                nickname,
                modifyNickNameForm.getNewNickname(),
                httpServletRequest);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 비밀번호 변경 ( 자신 프로필 페이지 )
    @PatchMapping("/profile/modify/password")
    public ResponseEntity<Message> modifyPassword(
            @RequestBody @Valid ModifyPasswordForm modifyPasswordForm,
            HttpServletRequest httpServletRequest
    ){
        Message resultMsg = userService.modifyPassword(
                modifyPasswordForm,
                httpServletRequest);
        return Message.make200Response(resultMsg.getMessage());
    }

}
